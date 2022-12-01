package net.chrissearle.kafka.kotlinproducer

import org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.CLIENT_ID_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.io.Serializable

@ConfigurationProperties(prefix = "kafka")
@ConstructorBinding
data class KafkaConfig(
    val clientId: String,
    val bootstrapServers: String,
    val topic: String,
    val serializer: String
) {
    fun configMap(): Map<String, Serializable> {
        return mapOf(
            CLIENT_ID_CONFIG to clientId,
            BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            KEY_SERIALIZER_CLASS_CONFIG to serializer,
            VALUE_SERIALIZER_CLASS_CONFIG to serializer,
        )
    }
}
