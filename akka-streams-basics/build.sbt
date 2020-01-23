ThisBuild / scalaVersion     := "2.12.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "net.chrisearle"

lazy val root = (project in file("."))
  .settings(
    name := "AkkaStreamsExample",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-stream" % "2.6.1"
    )
  )
