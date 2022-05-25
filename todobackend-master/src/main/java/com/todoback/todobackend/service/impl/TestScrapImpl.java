package com.todoback.todobackend.service.impl;

import com.todoback.todobackend.service.TestScrap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestScrapImpl implements TestScrap {
    private final ChromeDriver driver;

    public TestScrapImpl(ChromeDriver driver) {
        this.driver = driver;
    }
@PostConstruct
    public void scrapData() {
            driver.navigate().to("https://www.youtube.com/gaming/games/");
            WebElement bodyCookies = driver.findElement(By.tagName("body"));
            System.out.println(driver.getCurrentUrl());
            WebElement acceptButton = bodyCookies.findElement(By.xpath("//button[@aria-label='Accept all']"));
            acceptButton.click();
            System.out.println(driver.getCurrentUrl());
            WebElement bodyGames = driver.findElement(By.tagName("body"));
            List<WebElement> gameTitle = bodyGames.findElements(By.id("title"));
            List<WebElement> viewerCount = bodyGames.findElements(By.xpath("//span[@class='style-scope yt-formatted-string']"));
            List<WebElement> thumbnailLink = bodyGames.findElements(By.xpath("//img[@class='style-scope yt-img-shadow']"));
            printWebElements(gameTitle);
            printWebElements(viewerCount);
        }
        public void printWebElements(List<WebElement> webElementList) {
            List<String> stringToOutput = new ArrayList<>();
            for (int i = 0; i < webElementList.size(); i++) {
                if (!webElementList.get(i).getText().isEmpty()) {
                    stringToOutput.add(webElementList.get(i).getText());
                }
            }
            for (int i = 0; i < stringToOutput.size(); i++) {
                System.out.println(stringToOutput.get(i));
            }

        }
}
