package auth
import models.{FirebaseConfig, GetUserDataPayload, GetUserDataResponse, LoggedInResponse, WithEmailAndPassword}
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model.Uri
import client.HttpClient
import zio.{Has, IO, ZLayer}

object FirebaseAuth {

  val live: ZLayer[Has[FirebaseConfig] with HttpClient, Nothing, FirebaseAuth] =
    ZLayer.fromServices[FirebaseConfig, HttpClient.Service, Service] {
      (config, http) =>
        new Service {
          import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

          val secret: String = config.secret

          val identityToolkit: Uri = "https://identitytoolkit.googleapis.com/v1/"

          override def getUserData(idToken: String): IO[AuthError, GetUserDataResponse] = {
            http.post[GetUserDataPayload, GetUserDataResponse](GetUserDataPayload(idToken),
              Uri(s"${identityToolkit}accounts:lookup").withQuery(Query("key" -> secret))
            ).mapError(HttpError)
          }

          override def signIn(email: String, password: String): IO[AuthError, LoggedInResponse] = {
            http.post[WithEmailAndPassword, LoggedInResponse](WithEmailAndPassword(email, password),
              Uri(s"${identityToolkit}accounts:signInWithPassword").withQuery(Query("key" -> secret))
            ).mapError(HttpError)
          }

          override def signUp(email: String, password: String): IO[AuthError, LoggedInResponse] = {
            http.post[WithEmailAndPassword, LoggedInResponse](WithEmailAndPassword(email, password),
              Uri(s"${identityToolkit}accounts:signUp").withQuery(Query("key" -> secret))
            ).mapError(HttpError)
          }
        }
    }
  trait Service {
    def getUserData(idToken: String): IO[AuthError, GetUserDataResponse]
    def signIn(email: String, password: String): IO[AuthError, LoggedInResponse]
    def signUp(email: String, password: String): IO[AuthError, LoggedInResponse]
  }

  sealed trait AuthError

  case class HttpError(response: HttpClient.HttpError) extends AuthError

  case class Unexpected(cause: Throwable) extends AuthError

}
