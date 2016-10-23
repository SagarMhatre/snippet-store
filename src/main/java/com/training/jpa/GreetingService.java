package com.training.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

	@Autowired
	GreetingRepository greetingRepository;
	
	public Greeting dbOps(Greeting greeting){
		
		greeting.setId(3L);
		greeting.setText("Hello");
		greetingRepository.save(greeting);		
		
		
		greeting.setId(4L);
		greeting.setText("World");
		greetingRepository.save(greeting);		
				
		List<Greeting> greetings = greetingRepository.findAll();
		greetings.forEach(System.out::println);
		
		greeting.setText("World !");
		greetingRepository.save(greeting);
			
		
		greeting.setId(5L);
		greeting.setText("India");
		greetingRepository.save(greeting);
		
		
		greetings = greetingRepository.findAll();		
		greetings.forEach(System.out::println);
		
		greetingRepository.delete(1L);
		
		greetings = greetingRepository.findAll();		
		greetings.forEach(System.out::println);		
		
		greeting = greetingRepository.findOne(5L);
		
		return greeting;
	}
}
