package com.training.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


@SpringBootApplication
public class RaqbbitMQBootTestApplication {

	public static void main(String[] args) throws IOException, TimeoutException {
		ApplicationContext ac = SpringApplication.run(RaqbbitMQBootTestApplication.class, args);
		
		RabbitMQConnectorService rabbitMQConnectorService = ac.getBean(RabbitMQConnectorService.class);
		
		rabbitMQConnectorService.addListener("hello", true, new DefaultConsumer(rabbitMQConnectorService.getChannel()) {
	        @Override
	        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
	            throws IOException {
	          String message = new String(body, "UTF-8");
	          System.out.println(" [x] Received '" + message + "'");
	        }
	      });
		
		rabbitMQConnectorService.sendMessageToQueue("hello","Hello World");

	}

}
