package clients

import akka.actor.ActorSystem
import akka.kafka.scaladsl._
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.ActorMaterializer
import org.apache.kafka.clients.consumer.ConsumerConfig.{AUTO_COMMIT_INTERVAL_MS_CONFIG, AUTO_OFFSET_RESET_CONFIG, ENABLE_AUTO_COMMIT_CONFIG}
import org.apache.kafka.common.serialization.{Deserializer, Serdes}
import pureconfig.ConfigSource
import pureconfig.generic.auto._

case class Config(clientId: String,
                  groupId: String,
                  bootstrapServers: String,
                  enableAutoCommit: String,
                  autoCommitIntervalMs: String,
                  autoOffsetReset: String,
                  topic: String
                 )

object AkkaStreamsConsumer extends App {

  ConfigSource.default.load[Config] match {

    case Left(errors) =>
      println(errors)
      System.exit(1)

    case Right(config: Config) =>
      println("*** Starting Consumer ***")

      implicit val sys = ActorSystem()
      implicit val mat = ActorMaterializer()

      val consumerSettings: ConsumerSettings[String, Int] = buildConsumerSettings(sys, config)

      val subscription = Subscriptions.topics(Set(config.topic))

      Consumer
        .plainSource[String, Int](consumerSettings, subscription)
        .map(msg => msg.value())
        .runForeach(w => println(s"Consumed message with value $w"))
  }

  private def buildConsumerSettings(sys: ActorSystem, config: Config) = {
    val keyDeserializer = Serdes.String().deserializer()
    val valueDeserializer = Serdes.Integer().deserializer().asInstanceOf[Deserializer[Int]]

    ConsumerSettings(sys, keyDeserializer, valueDeserializer)
      .withBootstrapServers(config.bootstrapServers)
      .withProperties(
        AUTO_OFFSET_RESET_CONFIG -> config.autoOffsetReset,
        ENABLE_AUTO_COMMIT_CONFIG -> config.enableAutoCommit,
        AUTO_COMMIT_INTERVAL_MS_CONFIG -> config.autoCommitIntervalMs
      )
      .withGroupId(config.groupId)
      .withClientId(config.clientId)
  }
}




