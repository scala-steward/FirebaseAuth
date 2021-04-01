# Firebase Auth

[![Scala Steward badge](https://img.shields.io/badge/Scala_Steward-helping-blue.svg?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAMAAAARSr4IAAAAVFBMVEUAAACHjojlOy5NWlrKzcYRKjGFjIbp293YycuLa3pYY2LSqql4f3pCUFTgSjNodYRmcXUsPD/NTTbjRS+2jomhgnzNc223cGvZS0HaSD0XLjbaSjElhIr+AAAAAXRSTlMAQObYZgAAAHlJREFUCNdNyosOwyAIhWHAQS1Vt7a77/3fcxxdmv0xwmckutAR1nkm4ggbyEcg/wWmlGLDAA3oL50xi6fk5ffZ3E2E3QfZDCcCN2YtbEWZt+Drc6u6rlqv7Uk0LdKqqr5rk2UCRXOk0vmQKGfc94nOJyQjouF9H/wCc9gECEYfONoAAAAASUVORK5CYII=)](https://scala-steward.org)

REST client for Firebase authentication.

#### Exposes a ZIO interface. HTTP client is based on Akka Http.

## Usage
Add the following dependency:

```
libraryDependencies += "io.github.navidjalali" % "firebaseauth" % "0.0.4"
```
This library depends on Akka (typed, streams, http), zio and circe.
```scala
import akka.actor.ActorSystem
import client.HttpClient
import auth.FirebaseAuth
import models.FirebaseConfig
import zio.console.{Console, putStrLn}
import zio.{ExitCode, Managed, Task, URIO, ZIO, ZLayer}

object Main extends zio.App {

  val config = ZLayer.succeed(FirebaseConfig("such secret"))

  val email = "suchemail@suchdomain.wow"
  val password = "such secure password"

  val live: ZLayer[Any, Throwable, Console with FirebaseAuth] =
    Console.live and
      ZLayer.fromManaged(Managed.make(Task(ActorSystem("system")))(sys =>
        Task.fromFuture(_ => sys.terminate()).either)) >>>
        (config and HttpClient.live) >>>
        FirebaseAuth.live

  val app: ZIO[Console with FirebaseAuth, FirebaseAuth.AuthError, Unit] = for {
    auth <- ZIO.access[FirebaseAuth](_.get)
    signIn <- auth.signIn(email, password)
    userData <- auth.getUserData(signIn.idToken)
    _ <- putStrLn(userData.users.toString())
  } yield ()

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    app
      .catchAllCause(cause => putStrLn(cause.prettyPrint))
      .provideLayer(live)
      .exitCode
}
```
