package client

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.{Marshal, Marshaller}
import akka.http.scaladsl.model._
import akka.util.ByteString
import io.circe.Decoder
import io.circe.jawn.decode
import zio.{Has, IO, ZIO, ZLayer}

object HttpClient {

  trait Impl extends Service {
    implicit val actorSystem: ActorSystem

    def toFailedResponse(response: HttpResponse): ZIO[Any, HttpError, Nothing] =
      consumeEntity(response)
        .flatMap(entity =>
          IO.fail(ClientError(
            FailedResponse(
              response.status,
              entity,
              response.headers))))

    def consumeEntity(response: HttpResponse): ZIO[Any, Unexpected, String] =
      ZIO.fromFuture(_ => response.entity.dataBytes.runFold(ByteString.empty)(_ concat _))
        .map(_.utf8String)
        .mapError(Unexpected)

    override def request(req: HttpRequest): IO[HttpError, HttpResponse] =
      ZIO.fromFuture(_ => Http().singleRequest(req))
        .mapError(Unexpected)
        .flatMap {
          case r @ HttpResponse(StatusCodes.ClientError(_), _, _, _) =>
            toFailedResponse(r)
          case r @ HttpResponse(StatusCodes.ServerError(_), _, _, _) =>
            toFailedResponse(r)
          case other => IO.succeed(other)
        }

    def post[A, B](a: A, uri: Uri)(implicit m: Marshaller[A, MessageEntity], d: Decoder[B]): IO[HttpError, B] =
      for {
        message <- ZIO.fromFuture(implicit ec => Marshal(a).to[MessageEntity])
          .mapError(MarshallingError)

        response <- request(
          HttpRequest(
            method = HttpMethods.POST,
            uri = uri,
            entity = message,
          )
        )

        unparsed <- consumeEntity(response)

        result <- ZIO.fromEither(decode[B](unparsed))
          .mapError(ParsingError)
      } yield result
  }

  val live: ZLayer[Has[ActorSystem], Nothing, HttpClient] = ZLayer.fromService(system => {
    new Impl {
      override implicit val actorSystem: ActorSystem = system
    }
  })

  trait Service {
    def request(req: HttpRequest): IO[HttpError, HttpResponse]
    def post[A, B](payload: A, uri: Uri)(implicit m: Marshaller[A, MessageEntity], d: Decoder[B]): IO[HttpError, B]
  }

  case class FailedResponse(code: StatusCode, entity: String, headers: Seq[HttpHeader])

  sealed trait HttpError

  case class ClientError(response: FailedResponse) extends HttpError

  case class ServerError(response: FailedResponse) extends HttpError

  case class Unexpected(cause: Throwable) extends HttpError

  case class MarshallingError(cause: Throwable) extends HttpError

  case class ParsingError(error: io.circe.Error) extends HttpError

}
