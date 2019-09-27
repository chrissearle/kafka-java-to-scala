import java.time.Duration
import java.util.Properties

import org.apache.kafka.clients.producer.ProducerConfig.{BOOTSTRAP_SERVERS_CONFIG, CLIENT_ID_CONFIG, KEY_SERIALIZER_CLASS_CONFIG, VALUE_SERIALIZER_CLASS_CONFIG}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import pureconfig.ConfigSource
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

object ConfigProducer extends App {

  ConfigSource.default.load[Config] match {
    case Left(errors) =>
      println(errors)
      System.exit(1)

    case Right(config: Config) =>
      println("*** Starting Config Producer ***")

      val producer = new KafkaProducer[String, String](config.asProperties)

      (1 to 5).foreach { i =>
        producer.send(new ProducerRecord[String, String](config.topic, s"key-$i", s"value-$i"))
      }

      producer.close(Duration.ofMillis(100))

      println("### Stopping Config Producer ###")
  }
}
