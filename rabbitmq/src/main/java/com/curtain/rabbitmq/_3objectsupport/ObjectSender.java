package com.curtain.rabbitmq._3objectsupport;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class ObjectSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(Address address) {
        System.out.println("ObjectSender : " + address.toString());
        this.rabbitTemplate.convertAndSend("hello", address);
    }


}