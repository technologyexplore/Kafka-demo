package com.wassili;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaProducerDemo1 extends Thread {

    private final KafkaProducer<Integer, String> producer;

    private final String topic;
    private final boolean isAysnc;

    public KafkaProducerDemo1(String topic, boolean isAysnc) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "192.168.139.132:9092,192.168.139.130:9092,192.168.139.131:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaProducerDemo");
        properties.put(ProducerConfig.ACKS_CONFIG, "-1");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
//        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,
//                "com.wassili.MyPartition");  //Partition分区
        producer = new KafkaProducer<Integer, String>(properties);
        this.topic = topic;
        this.isAysnc = isAysnc;
    }



    @Override
    public void run() {
        int num = 0;
        while (num < 50) {
            String message = "message_" + num;
            System.out.println("begin send message: " + message );

            if (isAysnc) {
                producer.send(new ProducerRecord<Integer, String>(topic, message), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if (recordMetadata != null) {
                            System.out.println("async-offset: " + recordMetadata.offset() +
                                    "->partition:" + recordMetadata.partition());
                        }

                    }
                });
            } else { //同步发送
                try {
                    RecordMetadata recordMetadata = producer
                            .send(new ProducerRecord<Integer, String>(topic, message)).get();
                    System.out.println("sync-offset: " + recordMetadata.offset() +
                            "->partition:" + recordMetadata.partition());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            num++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        super.run();
    }

    public static void main(String[] args) {
        new KafkaProducerDemo1("test200", true).start();
    }
}
