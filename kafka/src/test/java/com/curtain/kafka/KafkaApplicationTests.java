package com.curtain.kafka;

import com.curtain.kafka.sender.KafkaSender;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaApplicationTests {

	@Autowired
	private KafkaSender kafkaSender;

	@Test
	public void contextLoads() {
	}

	@Test
	public void sender(){
		kafkaSender.send("hello kafka");
	}

}

