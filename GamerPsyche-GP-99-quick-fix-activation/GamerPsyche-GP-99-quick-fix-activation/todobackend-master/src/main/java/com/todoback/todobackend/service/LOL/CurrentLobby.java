package com.todoback.todobackend.service.LOL;

import com.todoback.todobackend.domain.Summoner;
import generated.LolSummonerSummoner;

import javax.annotation.PostConstruct;

public interface CurrentLobby {

    void findSummonersInLobby();
}
