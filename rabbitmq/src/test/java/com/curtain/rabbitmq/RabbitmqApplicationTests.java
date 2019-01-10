package com.curtain.rabbitmq;

import com.curtain.rabbitmq._1hello.HelloSender;
import com.curtain.rabbitmq._2manytomany.HelloSender2;
import com.curtain.rabbitmq._3objectsupport.Address;
import com.curtain.rabbitmq._3objectsupport.ObjectSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {

	@Autowired
	private HelloSender helloSender;

	@Autowired
	private HelloSender2 helloSender2;

	@Autowired
	private ObjectSender objectSender;

	/**
	 * 一对一  发送接收测试
	 * @throws Exception
	 */
	@Test
	public void hello() throws Exception {
		helloSender.send();
	}

	/**
	 * 多对多 发送接收测试
	 * @throws Exception
	 */
	@Test
	public void manyToMany()throws Exception{
		for (int i= 0;i<100;i++){
			helloSender.send();
			helloSender2.send();
		}
	}

	@Test
	public void object()throws Exception{
		Address address = new Address("温州","浙江","中国");

		objectSender.send(address);
	}


}

