package clients

import java.time.Duration
import java.util.Properties

import org.apache.kafka.clients.producer.ProducerConfig._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import pureconfig.generic.auto._

case class Config(clientId: String,
                  bootstrapServers: String,
                  topic: String,
                  serializer: String) {

  def asProperties: Properties = {
    val props = new Properties()

    props.put(CLIENT_ID_CONFIG, clientId)
    props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    props.put(KEY_SERIALIZER_CLASS_CONFIG, serializer)
    props.put(VALUE_SERIALIZER_CLASS_CONFIG, serializer)

    props
  }
}

object PureConfigProducer extends App {

  pureconfig.loadConfig[Config] match {
    case Left(errors) =>
      println(errors)
      System.exit(1)

    case Right(config: Config) =>
      println("*** Starting Basic Producer ***")

      val producer = new KafkaProducer[String, String](config.asProperties)

      (1 to 5).map { i =>
        val key = s"key-$i"
        val value = s"value-$i"

        val record = new ProducerRecord[String, String](config.topic, key, value)

        producer.send(record)
      }

      producer.close(Duration.ofMillis(100))

      println("### Stopping Basic Producer ###")
  }
}

