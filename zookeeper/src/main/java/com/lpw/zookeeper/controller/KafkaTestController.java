package com.lpw.zookeeper.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

/**
 * @author v_pwlu 2019/3/11
 */
@Slf4j
@RestController
public class KafkaTestController {
    private final KafkaProducer kafkaProducer;
    private final KafkaConsumer kafkaConsumer;

    @Autowired
    public KafkaTestController(KafkaProducer kafkaProducer,
                               KafkaConsumer kafkaConsumer) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaConsumer = kafkaConsumer;
    }


    @GetMapping(value = "/kafka/producer/test")
    public void testKafkaProducer() {
        ProducerRecord<String , String> record = null;
        for (int i = 0; i < 1000; i++) {
            record = new ProducerRecord<>("test-topic", "value"+(int)(10*(Math.random())));
            //发送消息
            kafkaProducer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (null != e){
                        log.info("send error" + e.getMessage());
                    }else {
                        System.out.println(String.format("offset:%s,partition:%s",recordMetadata.offset(),recordMetadata.partition()));
                    }
                }
            });
        }
    }


    @GetMapping(value = "/kafka/consumer/test")
    public void testKafkaConsumer() {
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(10);
            for (ConsumerRecord<String, String> record : records) {
                log.info(record.toString());
            }
        }
    }
}
