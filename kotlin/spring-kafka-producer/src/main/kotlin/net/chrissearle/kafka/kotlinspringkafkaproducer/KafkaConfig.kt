package net.chrissearle.kafka.kotlinspringkafkaproducer

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaConfig(
    @Value("\${kafka.bootstrap}") private val servers: String,
    @Value("\${kafka.topic}") private val topic: String
) {

    @Bean
    fun kafkaAdmin() = KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to servers))

    @Bean
    fun topic() = NewTopic(topic, 1, 1.toShort())
}