package com.example.LOLDesktopService.controller;

import com.example.LOLDesktopService.service.CurrentLobby;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class TaskController {
    @Autowired
    CurrentLobby currentLobby;

    @PostConstruct
    public void testCurrentLobby(){
        currentLobby.findSummonersInLobby();
    }
}
