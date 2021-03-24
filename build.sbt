name := "FirebaseAuth"

scalaVersion := "2.13.5"

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

val AkkaVersion = "2.6.10"
val zioVersion = "1.0.3"
val log4jVersion = "2.14.0"
val AkkaHttpVersion = "10.2.1"
val circeVersion = "0.12.3"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-streams" % zioVersion,
  "dev.zio" %% "zio-test"          % zioVersion % "test",
  "dev.zio" %% "zio-test-sbt"      % zioVersion % "test",
)

testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")

libraryDependencies ++= Seq(
  "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-1.2-api" % log4jVersion
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "2.0.2"
)

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies ++= Seq(
  "de.heikoseeberger" %% "akka-http-circe" % "1.31.0"
)
