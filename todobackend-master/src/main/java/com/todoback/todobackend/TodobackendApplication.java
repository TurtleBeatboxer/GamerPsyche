package com.todoback.todobackend;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TodobackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodobackendApplication.class, args);
		//TU CHUJU WSTAW KLUCZ
		Orianna.setRiotAPIKey("RGAPI-fffedd9f-0f11-4c5c-b2ce-804c374a77bc");
	}

}
