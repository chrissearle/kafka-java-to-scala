package net.chrissearle.kafka.kotlinconsumer

import org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.io.Serializable

@ConfigurationProperties(prefix = "kafka")
@ConstructorBinding
data class KafkaConfig(
    val groupId: String,
    val bootstrapServers: String,
    val enableAutoCommit: String,
    val autoCommitIntervalMs: String,
    val autoOffsetReset: String,
    val deserializer: String,
    val topic: String
) {
    fun configMap(): Map<String, Serializable> {
        return mapOf(
            GROUP_ID_CONFIG to groupId,
            BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ENABLE_AUTO_COMMIT_CONFIG to enableAutoCommit,
            AUTO_COMMIT_INTERVAL_MS_CONFIG to autoCommitIntervalMs,
            AUTO_OFFSET_RESET_CONFIG to autoOffsetReset,
            KEY_DESERIALIZER_CLASS_CONFIG to deserializer,
            VALUE_DESERIALIZER_CLASS_CONFIG to deserializer
        )
    }
}
