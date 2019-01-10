package com.curtain.rabbitmq._3objectsupport;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "hello")
public class ObjectReceiver {

    @RabbitHandler
    public void process(Address address) {
        System.out.println("ObjectReceiver  : " + address);
    }

}