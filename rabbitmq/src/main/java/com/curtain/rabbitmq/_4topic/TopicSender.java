package com.curtain.rabbitmq._4topic;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Curtain
 * @date 2019/1/10 13:59
 */
@Component
public class TopicSender {

	@Autowired
    private AmqpTemplate rabbitTemplate;


//	public void send() {
//		String context = "hi, i am message all";
//		System.out.println("Sender : " + context);
//		this.rabbitTemplate.convertAndSend("exchange", "topic.1", context);
//	}

	/**
	 * 发送信息到exchange交换机 topic.message 队列
	 */
	public void send1() {
		String context = "hi, i am message 1";
		System.out.println("Sender : " + context);
		this.rabbitTemplate.convertAndSend("exchange", "topic.message", context);
	}

	/**
	 * 发送信息到exchange交换机 topic.messages 队列
	 */
	public void send2() {
		String context = "hi, i am messages 2";
		System.out.println("Sender : " + context);
		this.rabbitTemplate.convertAndSend("exchange", "topic.messages", context);
	}

}