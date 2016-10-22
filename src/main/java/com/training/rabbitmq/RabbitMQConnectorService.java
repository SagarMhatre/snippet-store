package com.training.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.MessageProperties;

@Service
public class RabbitMQConnectorService {

	@Autowired
	private Channel channel;
	
	public void sendMessageToQueue(String queueName, String message) throws IOException, TimeoutException{
		
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
	}
	
	public void addListener(String queueName, boolean autoAck, Consumer consumer) throws IOException{
		channel.basicConsume(queueName, autoAck, consumer);
	}

	public Channel getChannel() {
		return channel;
	}
}
