package com.curtain.rabbitmq._5fanout;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Curtain
 * @date 2019/1/10 15:09
 */
@Component
public class FanoutSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hi, fanout msg";
        System.out.println("Sender : " + context);
        rabbitTemplate.convertAndSend("fanoutExchange","",context);
    }
}
