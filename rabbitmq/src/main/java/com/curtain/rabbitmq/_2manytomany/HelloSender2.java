package com.curtain.rabbitmq._2manytomany;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
/**
 * @author Curtain
 * @date 2019/1/10 10:26
 */

@Component
public class HelloSender2 {

	@Autowired
    private AmqpTemplate rabbitTemplate;

	public void send() {
		String context = "hello " + new Date();
		System.out.println("Sender2 : " + context);
		this.rabbitTemplate.convertAndSend("hello", context);
	}

}