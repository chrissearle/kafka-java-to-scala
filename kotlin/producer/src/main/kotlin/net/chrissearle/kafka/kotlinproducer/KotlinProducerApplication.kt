package net.chrissearle.kafka.kotlinproducer

import mu.KotlinLogging
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

private val logger = KotlinLogging.logger {}

@SpringBootApplication
@EnableConfigurationProperties(KafkaConfig::class)
class KotlinProducerApplication(val kafkaConfig: KafkaConfig) : CommandLineRunner {

	override fun run(vararg args: String?) {
		logger.info { "Starting up producer" }

		val producer = KafkaProducer<String, String>(kafkaConfig.configMap())

		(1..5).forEach {
			logger.info { "### Sending $it ###" }
			producer.send(ProducerRecord(kafkaConfig.topic, "key-$it", "value-$it"))
		}

		producer.close()

		logger.info { "Producer complete" }
	}
}

fun main(args: Array<String>) {
	runApplication<KotlinProducerApplication>(*args)
}
