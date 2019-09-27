package clients

import akka.actor.ActorSystem
import akka.kafka.scaladsl._
import akka.kafka.{ProducerMessage, ProducerSettings}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{Serdes, Serializer}
import pureconfig.ConfigSource
import pureconfig.generic.auto._

case class Config(bootstrapServers: String,
                  topic: String)

object AkkaStreamsProducer extends App {

  ConfigSource.default.load[Config] match {

    case Left(errors) =>
      println(errors)
      System.exit(1)

    case Right(config: Config) =>
      println("*** Starting Producer ***")

      implicit val sys = ActorSystem()
      implicit val mat = ActorMaterializer()

      val producerSettings: ProducerSettings[String, Int] = buildProducerSettings(sys, config)

      Source
        .fromIterator(() => (0 to 10000).toIterator)
        .map(i => i * 2)
        .map { i =>
          ProducerMessage.Message(new ProducerRecord[String, Int](config.topic, i), i)
        }
        .via(Producer.flexiFlow(producerSettings))
        .runWith {
          Sink.foreach(res => println(s"Wrote ${res.passThrough} to ${config.topic}"))
        }
  }

  private def buildProducerSettings(sys: ActorSystem, config: Config) = {
    val keySerializer = Serdes.String().serializer()
    val valueSerializer = Serdes.Integer().serializer().asInstanceOf[Serializer[Int]]

    ProducerSettings(sys, keySerializer, valueSerializer)
      .withBootstrapServers(config.bootstrapServers)
  }
}
