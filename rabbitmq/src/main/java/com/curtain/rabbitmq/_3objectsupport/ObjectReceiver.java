package com.curtain.rabbitmq._3objectsupport;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
/**
 * @author Curtain
 * @date 2019/1/10 10:42
 */
@Component
@RabbitListener(queues = "hello")
public class ObjectReceiver {

    @RabbitHandler
    public void process(Address address) {
        System.out.println("ObjectReceiver  : " + address);
    }

}