name := "pureconfig-producer"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients" % "2.2.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "ch.qos.logback" % "logback-core" % "1.2.3",
  "com.github.pureconfig" %% "pureconfig" % "0.10.2"
)
