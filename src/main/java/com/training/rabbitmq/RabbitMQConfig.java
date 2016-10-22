package com.training.rabbitmq;



import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Configuration 
	//tags the class as a source of bean definitions for the application context.
public class RabbitMQConfig {	

    @Bean
    Channel getChannel() throws IOException, TimeoutException {
    	ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    return connection.createChannel();
    }

  
}
