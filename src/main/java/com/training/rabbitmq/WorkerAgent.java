package com.training.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class WorkerAgent extends DefaultConsumer{

	private final String id;
	
	public WorkerAgent(Channel channel, String id) {
		super(channel);
		this.id = id;
	}
	
	@Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
        throws IOException {
		 String message = new String(body, "UTF-8");
		 System.out.println("	[ " + id +" ] Received '" + message + "'");
	}


}
