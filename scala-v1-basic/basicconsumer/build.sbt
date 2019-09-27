ThisBuild / scalaVersion     := "2.12.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "net.chrissearle"

lazy val root = (project in file("."))
  .settings(
    name := "BasicConsumer",
    libraryDependencies ++= Seq(
      "org.apache.kafka" % "kafka-clients" % "2.3.0"
    )
  )

