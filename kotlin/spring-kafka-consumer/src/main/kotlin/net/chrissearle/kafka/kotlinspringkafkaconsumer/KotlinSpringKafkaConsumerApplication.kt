package net.chrissearle.kafka.kotlinspringkafkaconsumer

import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment

private val logger = KotlinLogging.logger {}

@SpringBootApplication
class KotlinSpringKafkaConsumerApplication: CommandLineRunner {
    @KafkaListener(topics = ["\${kafka.topic}"], groupId = "\${kafka.group}", containerFactory = "kafkaListenerContainerFactory")
    fun listen(consumerRecord: ConsumerRecord<Any, Any>, ack: Acknowledgment) {
        logger.info { "Message received $consumerRecord" }
        ack.acknowledge()
    }

    override fun run(vararg args: String?) {
        while (true) {}
    }
}

fun main(args: Array<String>) {
    runApplication<KotlinSpringKafkaConsumerApplication>(*args)
}
