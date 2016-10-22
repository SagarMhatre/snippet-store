package com.training.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.MessageProperties;

public class RabbitMQTest {

	public static void main(String[] args) {
		try {
			sendMessage("hello","Hello World");
			receiveMessage("hello");
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	private static void receiveMessage(String queueName) throws IOException, TimeoutException{
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();	// instances are safe for use by multiple threads. 
														// Requests into a Channel are serialized, with only one thread running commands at a time

	    Consumer consumer = new DefaultConsumer(channel) {
	        @Override
	        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
	            throws IOException {
	          String message = new String(body, "UTF-8");
	          System.out.println(" [x] Received '" + message + "'");
	        }
	      };
	  	
	  	boolean autoAck = true ; //set it to false if message should be delivered to other worker if this fails 
	  	
	  	channel.basicConsume(queueName, autoAck, consumer);
	}
	
	private static void sendMessage(String queueName, String message) throws IOException, TimeoutException{
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();	// instances are safe for use by multiple threads. 
														// Requests into a Channel are serialized, with only one thread running commands at a time

		// To send, we must declare a queue for us to send to; 
	    // then we can publish a message to the queue:

		boolean durable = true;	//to make sure that RabbitMQ will never lose our queue. 
		
	    channel.queueDeclare(queueName, durable, false, false, null);    
	    channel.basicPublish(
				"", 									// "" =  we were using a default exchange
				queueName, 								// Queue Name or Routing Key 
				MessageProperties.PERSISTENT_TEXT_PLAIN	//to mark our messages as persistent  else null
						/*
								Although it tells RabbitMQ to save the message to disk, 
								there is still a short time window when RabbitMQ has accepted 
								a message and hasn't saved it yet. 
								Also, RabbitMQ doesn't do fsync(2) for every message 
								-- it may be just saved to cache and not really written to the disk.
								If you need a stronger guarantee then you can use publisher confirms.
						*/
			 , message.getBytes());
		
		channel.close();
	    connection.close();
	}

}
