package com.curtain.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Curtain
 * @date 2019/1/11 13:44
 */
@Component
public class KafkaConsumer {
    @KafkaListener(topics = {"test"})
    public void listen(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {

            Object message = kafkaMessage.get();

            System.out.println("kafka record = " + record);
            System.out.println("kafka message = " + message);
        }

    }
}
