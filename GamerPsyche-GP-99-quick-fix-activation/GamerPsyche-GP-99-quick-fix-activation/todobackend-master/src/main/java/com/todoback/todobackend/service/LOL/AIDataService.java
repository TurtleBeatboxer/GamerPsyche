package com.todoback.todobackend.service.LOL;

import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;

public interface AIDataService {
    //void userSearch(String lolUsername, int championId);
    void userSearchBrute(String lolUsername, int championId) throws Exception;
}
