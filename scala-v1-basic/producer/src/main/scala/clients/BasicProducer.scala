package clients

import java.time.Duration
import java.util.Properties

import org.apache.kafka.clients.producer.ProducerConfig.{BOOTSTRAP_SERVERS_CONFIG, CLIENT_ID_CONFIG, KEY_SERIALIZER_CLASS_CONFIG, VALUE_SERIALIZER_CLASS_CONFIG}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

object BasicProducer {

  def main(args: Array[String]): Unit = {

    println("*** Starting Basic Producer ***")

    val settings = new Properties()

    settings.put(CLIENT_ID_CONFIG, "basic-producer")
    settings.put(BOOTSTRAP_SERVERS_CONFIG, "kafka:9092")
    settings.put(KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getCanonicalName)
    settings.put(VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getCanonicalName)

    val producer = new KafkaProducer[String, String](settings)

    val topic = "basic-topic"

    for (i <- 1 to 5) {
      val key = "key-" + i
      val value = "value-" + i

      val record = new ProducerRecord[String, String](topic, key, value)

      producer.send(record)
    }

    producer.close(Duration.ofMillis(100))

    println("### Stopping Basic Producer ###")

  }
}
