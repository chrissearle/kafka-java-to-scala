package net.chrissearle.kafka.kotlinconsumer

import mu.KotlinLogging
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import java.time.Duration

private val logger = KotlinLogging.logger {}

@SpringBootApplication
@EnableConfigurationProperties(KafkaConfig::class)
class KotlinConsumerApplication(val kafkaConfig: KafkaConfig) : CommandLineRunner {

    override fun run(vararg args: String?) {
        logger.info { "Starting up consumer" }

        KafkaConsumer<String, String>(kafkaConfig.configMap()).use { consumer ->
            consumer.subscribe(listOf(kafkaConfig.topic))

            while (true) {
                val records = consumer.poll(Duration.ofMillis(100))

                records.forEach { record ->
                    logger.info { "offset = ${record.offset()}, key = ${record.key()}, value = ${record.value()}" }
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<KotlinConsumerApplication>(*args)
}
