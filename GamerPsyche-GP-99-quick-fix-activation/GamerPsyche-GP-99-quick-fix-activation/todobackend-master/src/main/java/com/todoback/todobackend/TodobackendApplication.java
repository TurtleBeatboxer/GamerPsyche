package com.todoback.todobackend;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.stirante.lolclient.ClientApi;
import com.stirante.lolclient.ClientConnectionListener;
import generated.LolChampionsCollectionsChampion;
import generated.LolChampionsCollectionsChampionSkin;
import generated.LolSummonerSummoner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.util.Date;

@EnableScheduling
@SpringBootApplication
public class TodobackendApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(TodobackendApplication.class, args);
		//Wstaw Klucz
		Orianna.setRiotAPIKey("RGAPI-0e7f119b-b9aa-4e9f-8d81-b05debe7a61f");
	}



}
