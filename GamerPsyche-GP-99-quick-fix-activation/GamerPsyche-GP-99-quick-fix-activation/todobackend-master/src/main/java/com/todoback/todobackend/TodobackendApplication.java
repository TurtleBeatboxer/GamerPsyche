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
		Orianna.setRiotAPIKey("RGAPI-9e3d5349-8b5d-42b3-841a-e6b9a332ad9f");
	}

}
