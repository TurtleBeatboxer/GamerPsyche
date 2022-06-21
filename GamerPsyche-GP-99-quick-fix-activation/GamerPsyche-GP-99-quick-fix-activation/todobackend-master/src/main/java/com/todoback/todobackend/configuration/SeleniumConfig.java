/*
package com.todoback.todobackend.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.beans.JavaBean;
import java.io.File;
import java.io.FileNotFoundException;

@Configuration
public class SeleniumConfig {
    @Value("${selenium.webdriver.chrome.path}")
    private String seleniumWebdriverPath;


    @PostConstruct
    void postConstruct() throws FileNotFoundException {
        File file = ResourceUtils.getFile(seleniumWebdriverPath);
        String absolutePath = file.getAbsolutePath();
        File f = new File(absolutePath);
        if(f.exists() && !f.isDirectory()) {
            System.setProperty("webdriver.chrome.driver", absolutePath);
        } else {
        }
    }

    @Bean
    public ChromeDriver driver() {
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        return new ChromeDriver(chromeOptions);
    }
}
*/
