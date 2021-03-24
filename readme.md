# Firebase Auth

## REST client for Firebase authentication.
#### Exposes a ZIO interface. HTTP client is based on Akka Http.

## Usage
Add the following dependency:

```
libraryDependencies += "io.github.navidjalali" % "firebaseauth" % "0.0.1"
```
This library depends on Akka (typed, streams, http), zio and circe.
```scala
import akka.actor.ActorSystem
import client.HttpClient
import auth.FirebaseAuth
import zio.console.{Console, putStrLn}
import zio.{ExitCode, Managed, Task, URIO, ZIO, ZLayer}

object Main extends zio.App {

  val secret = "such secret read from config"

  val email = "suchemail@suchdomain.wow"
  val password = "such secure password"

  val live: ZLayer[Any, Throwable, Console with FirebaseAuth] =
    Console.live and
      ZLayer.fromManaged(Managed.make(Task(ActorSystem("system")))(sys =>
        Task.fromFuture(_ => sys.terminate()).either)) >>>
        HttpClient.live >>>
        FirebaseAuth.live(secret)

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

## Doggo
![image](https://user-images.githubusercontent.com/5600005/111153350-235dc780-8592-11eb-99c5-a1649a9f0189.png)
