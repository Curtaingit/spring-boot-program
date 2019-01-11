package com.curtain.kafka.sender;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Curtain
 * @date 2018/12/21 10:40
 */

@Component
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //发送消息方法
    public void send(String msg) {
        System.out.println("kafka send message " + msg);
        kafkaTemplate.send("test", msg);
    }

}
