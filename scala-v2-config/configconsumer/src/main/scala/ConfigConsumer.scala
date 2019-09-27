import java.time.Duration
import java.util.Properties

import org.apache.kafka.clients.consumer.ConsumerConfig._
import org.apache.kafka.clients.consumer.KafkaConsumer
import pureconfig.ConfigSource
import pureconfig.generic.auto._

import scala.collection.JavaConverters._

case class Config(groupId: String,
                  bootstrapServers: String,
                  enableAutoCommit: String,
                  autoCommitIntervalMs: String,
                  autoOffsetReset: String,
                  deserializer: String,
                  topic: String
                 ) {
  def asProperties: Properties = {
    val props = new Properties()

    props.put(GROUP_ID_CONFIG, groupId)
    props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    props.put(ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit)
    props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitIntervalMs)
    props.put(AUTO_OFFSET_RESET_CONFIG, autoOffsetReset)
    props.put(KEY_DESERIALIZER_CLASS_CONFIG, deserializer)
    props.put(VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)

    props
  }
}

object ConfigConsumer extends App {
  ConfigSource.default.load[Config] match {
    case Left(errors) =>
      println(errors)
      System.exit(1)

    case Right(config: Config) =>
      println("*** Starting Config Consumer ***")

      val consumer = new KafkaConsumer[String, String](config.asProperties)

      try {
        consumer.subscribe(List(config.topic).asJava)

        while (true) {
          val records = consumer.poll(Duration.ofMillis(100)).asScala

          for (record <- records) {
            println(s"offset = ${record.offset}, key = ${record.key}, value = ${record.value}")
          }
        }
      } finally {
        consumer.close()
      }

  }
}
