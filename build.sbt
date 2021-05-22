name := "FirebaseAuth"

scalaVersion := "2.13.6"

inThisBuild(List(
  organization := "io.github.navidjalali",
  homepage := Some(url("https://navidjalali.github.io")),
  licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(
    Developer(
      "navidjalali",
      "Navid Jalali",
      "nvdeth0@gmail.com",
      url("https://navidjalali.github.io")
    )
  )
))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/navidjalali/FirebaseAuth"),
    "scm:git@github.com:navidjalali/FirebaseAuth.git"
  )
)

sonatypeCredentialHost := "s01.oss.sonatype.org"
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"

val AkkaVersion = "2.6.14"
val AkkaHttpVersion = "10.2.4"
val zioVersion = "1.0.8"
val circeVersion = "0.13.0"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % zioVersion,
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
)

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies ++= Seq(
  "de.heikoseeberger" %% "akka-http-circe" % "1.36.0"
)
