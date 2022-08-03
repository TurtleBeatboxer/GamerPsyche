package com.example.LOLDesktopService.controller;

import com.example.LOLDesktopService.service.CurrentLobby;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class TaskController {
    @Autowired
    CurrentLobby currentLobby;

    @PostConstruct
    public void find() throws IOException {

        currentLobby.findSummonersInLobby();
    }
}