import java.time.Duration
import java.util.Properties

import org.apache.kafka.clients.consumer.ConsumerConfig._
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer

import collection.JavaConverters._

object BasicConsumer {

  def main(args: Array[String]): Unit = {

    println("*** Starting Basic Consumer ***")

    val settings = new Properties()

    settings.put(GROUP_ID_CONFIG, "basic-consumer")
    settings.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:29092")
    settings.put(ENABLE_AUTO_COMMIT_CONFIG, "true")
    settings.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")
    settings.put(AUTO_OFFSET_RESET_CONFIG, "earliest")
    settings.put(KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    settings.put(VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])

    val consumer = new KafkaConsumer[String, String](settings)

    val topic = "scala-v1-basic-topic"

    try {
      consumer.subscribe(List(topic).asJava)

      while (true) {
        val records = consumer.poll(Duration.ofMillis(100))

        for (record <- records.asScala) {
          println(s"offset = ${record.offset}, key = ${record.key}, value = ${record.value}")
        }
      }
    } finally {
      consumer.close()
    }
  }
}
