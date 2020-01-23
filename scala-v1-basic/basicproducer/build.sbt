ThisBuild / scalaVersion     := "2.12.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "net.chrisearle"

lazy val root = (project in file("."))
  .settings(
    name := "BasicProducer",
    libraryDependencies ++= Seq(
      "org.apache.kafka" % "kafka-clients" % "2.4.0"
    )
  )

