package com.todoback.todobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TodobackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodobackendApplication.class, args);
	}

}
