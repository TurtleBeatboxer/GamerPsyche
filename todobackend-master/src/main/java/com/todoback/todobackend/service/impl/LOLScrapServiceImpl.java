package com.todoback.todobackend.service.impl;

import com.todoback.todobackend.domain.User;
import com.todoback.todobackend.repository.UserRepository;
import com.todoback.todobackend.service.LOLScrapService;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class LOLScrapServiceImpl implements LOLScrapService {
    private final ChromeDriver driver;

    @Autowired
    UserRepository userRepository;

    public LOLScrapServiceImpl(ChromeDriver driver) {
        this.driver = driver;
    }

    @PostConstruct
    public void scrapData() {
        String testUsername = "Dupa";
        driver.navigate().to(prepareUserLink(testUsername));
        System.out.println(driver.getCurrentUrl());
        WebElement webElement = driver.findElementByClassName("m-t5iiuf");
        System.out.println(webElement.getText());

    }


    public String prepareUserLink(String username) {
       Optional<User> optionalUser = userRepository.findByUsername(username);
        String link = "https://app.mobalytics.gg/lol/profile/";
        if (optionalUser.isPresent()) {
            link = link + optionalUser.get().getLOLServer()
                    + "/"
                    + optionalUser.get().getLOLUsername()
                    + "/"
                    + "overview";
        }
        return link;
    }
}
