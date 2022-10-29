package com.todoback.todobackend.service.LOL;

import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;

public interface AIDataService {
    void userSearchBrute(String lolUsername) throws Exception;
}
