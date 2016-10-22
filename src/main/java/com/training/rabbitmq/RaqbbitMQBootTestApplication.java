package com.training.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RaqbbitMQBootTestApplication {

	public static void main(String[] args) throws IOException, TimeoutException {
		ApplicationContext ac = SpringApplication.run(RaqbbitMQBootTestApplication.class, args);
		
		RabbitMQConnectorService rabbitMQConnectorService = ac.getBean(RabbitMQConnectorService.class);
		
		for(int i=0;i<10;i++){
			rabbitMQConnectorService.addListener("hello", true, 
					new WorkerAgent(rabbitMQConnectorService.getChannel(), "WorkerAgent "+i));
		}
		
		for(int i=0;i<100;i++){
			rabbitMQConnectorService.sendMessageToQueue("hello","Hello World " + i);
		}
		
		rabbitMQConnectorService.close();

	}

}
