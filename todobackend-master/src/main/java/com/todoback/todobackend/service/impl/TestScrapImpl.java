package com.todoback.todobackend.service.impl;

import com.todoback.todobackend.domain.RecentActivity;
import com.todoback.todobackend.service.TestScrap;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TestScrapImpl implements TestScrap {
    private final ChromeDriver driver;

    public TestScrapImpl(ChromeDriver driver) {
        this.driver = driver;
    }

    @PostConstruct
    public void scrapData() {
        List<RecentActivity> ActivityList = new ArrayList<>();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://app.mobalytics.gg/lol/profile/eune/koczokok/overview");
        WebElement body = driver.findElement(By.tagName("body"));
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[1]/div[1]/div[1]/div[4]/div/div/header/div[1]/div/div/div[2]/div/button")));
        WebElement refreshButton = body.findElement(
                By.xpath("/html/body/div[1]/div[1]/div[1]/div[4]/div/div/header/div[1]/div/div/div[2]/div/button"));
        refreshButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Consent']")));
        WebElement cookieButton = body.findElement(By.xpath("//button[@aria-label='Consent']"));
        cookieButton.click();
        body.sendKeys(Keys.PAGE_DOWN);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "/html/body/div[1]/div[1]/div[1]/div[4]/div/div/main/div[1]/div[5]/div[1]/div[1]/div/*[name()='svg']")));
        WebElement bigone = driver.findElement(By.xpath(
                "/html/body/div[1]/div[1]/div[1]/div[4]/div/div/main/div[1]/div[5]/div[1]/div[1]/div/*[name()='svg']/*[name()='g'][2]"));
        List<WebElement> gs = bigone.findElements(By.xpath("./*"));
        System.out.println(gs.size());
        outerloop: for (int i = 1; i <= gs.size(); i++) {
            String path = String.format(
                    "/html/body/div[1]/div[1]/div[1]/div[4]/div/div/main/div[1]/div[5]/div[1]/div[1]/div/*[name()='svg']/*[name()='g'][2]/*[name()='g'][%d]",
                    i);
            WebElement small = driver.findElement(By.xpath(path));
            List<WebElement> s = small.findElements(By.xpath("./*"));
            System.out.println(s.size());
            for (int j = 1; j <= s.size(); j++) {

                String string = String.format(
                        "/html/body/div[1]/div[1]/div[1]/div[4]/div/div/main/div[1]/div[5]/div[1]/div[1]/div/*[name()='svg']/*[name()='g'][2]/*[name()='g'][%d]/*[name()='rect'][%2d]",
                        i, j);
                System.out.println(string);
                driver.findElement(By.xpath(string)).click();
                String attribute = driver.findElement(By.xpath(string)).getAttribute("aria-describedby");
                if (attribute != null) {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(attribute)));
                    WebElement e = body.findElement(By.id(attribute));
                    List<WebElement> el = e.findElements(By.xpath("./*"));

                    RecentActivity rect = new RecentActivity();

                    String d = el.get(0).getText();
                    String data[] = d.split("\\r?\\n");
                    rect.setDate(data[1]);
                    rect.setHoursPlayed(data[3]);
                    rect.setGamesRatio(data[5]);
                    rect.setWinRatioByString(data[7]);
                    ActivityList.add(rect);

                }

            }
        }
        printActivity(ActivityList);
        
        driver.close();
    

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

    public void printActivity(List<RecentActivity> rect) {
        List<String> stringToOutput = new ArrayList<>();
        for (int i = 0; i < rect.size(); i++) {

            stringToOutput.add(rect.get(i).getDate());
            stringToOutput.add(rect.get(i).getGamesRatio());
            stringToOutput.add(rect.get(i).getHoursPlayed());
            stringToOutput.add(rect.get(i).getWinRatio());

        }
        for (int i = 0; i < stringToOutput.size(); i++) {
            System.out.println(stringToOutput.get(i));
        }
    }

}
