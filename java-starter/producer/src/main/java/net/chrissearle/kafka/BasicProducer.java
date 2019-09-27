package net.chrissearle.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class BasicProducer {

  public static final String TOPIC = "hello-world-topic";

  public static void main(String[] args) {

    System.out.println("*** Starting Basic Producer ***");

    Properties settings = new Properties();

    settings.put("client.id", "basic-producer");
    settings.put("bootstrap.servers", "localhost:29092");
    settings.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    settings.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    try (KafkaProducer<String, String> producer = new KafkaProducer<>(settings)) {
      for (int i = 1; i <= 5; i++) {
        final String key = "key-" + i;
        final String value = "value-" + i;

        System.out.println("### Sending " + i + " ###");

        producer.send(new ProducerRecord<>(TOPIC, key, value));
      }
    }
  }
}
