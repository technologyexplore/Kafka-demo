package com.wassili;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerDemo2 extends Thread {

    private final KafkaConsumer kafkaConsumer;

    public KafkaConsumerDemo2(String topic) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "192.168.139.132:9092,192.168.139.130:9092,192.168.139.131:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaConsumerDemo1");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        kafkaConsumer = new KafkaConsumer(properties);
        kafkaConsumer.subscribe(Collections.singleton(topic));
//        TopicPartition topicPartition = new TopicPartition(topic, 0);  //指定分区
//        kafkaConsumer.assign(Arrays.asList(topicPartition));
    }

    @Override
    public void run() {
        int num = 0;
        while (true) {
            ConsumerRecords<Integer, String> consumerRecords = kafkaConsumer.poll(1000);
            for (ConsumerRecord consumerRecord : consumerRecords) {
                System.out.println(consumerRecord.partition() + "-> message recive: " + consumerRecord.value());
            }
        }

    }

    public static void main(String[] args) {

        new KafkaConsumerDemo2("test200").start();
//        System.out.println(("KafkaConsumerDemo1".hashCode())%50);
    }
}
