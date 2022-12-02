package net.chrissearle.kafka.kotlinspringkafkaproducer

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.core.KafkaTemplate

private val logger = KotlinLogging.logger {}

@SpringBootApplication
class KotlinSpringKafkaProducerApplication(
    val kafkaTemplate: KafkaTemplate<String, String>,
    @Value("\${kafka.topic}") private val topic: String
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        logger.info { "Starting up producer" }

        (1..5).forEach {
            logger.info { "### Sending $it ###" }
            kafkaTemplate.send(topic, "key-$it", "value-$it")
        }

        logger.info { "Producer complete" }
    }
}

fun main(args: Array<String>) {
    runApplication<KotlinSpringKafkaProducerApplication>(*args)
}
