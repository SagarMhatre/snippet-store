package com.training.jpa;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class JPABootTestApplication {

	public static void main(String[] args) throws IOException, TimeoutException {
		ApplicationContext ac = SpringApplication.run(JPABootTestApplication.class, args);
		
		GreetingService greetingService = ac.getBean(GreetingService.class);
		

		Greeting g = greetingService.dbOps(new Greeting());
		
		System.out.println(g.toString());
		
	}

}
