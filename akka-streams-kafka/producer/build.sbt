ThisBuild / scalaVersion     := "2.12.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "net.chrisearle"

lazy val root = (project in file("."))
  .settings(
    name := "AkkaProducer",
    libraryDependencies ++= Seq(
      "org.apache.kafka" % "kafka-clients" % "2.3.0",
      "com.github.pureconfig" %% "pureconfig" % "0.12.0",
      "com.typesafe.akka" %% "akka-stream-kafka" % "1.0.5"
    )
  )
