package com.todoback.todobackend.domain;

import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;

public class Summoner {
    private String summonerName;
    private LeagueShard leagueShard;

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public LeagueShard getLeagueShard() {
        return leagueShard;
    }

    public void setLeagueShard(LeagueShard leagueShard) {
        this.leagueShard = leagueShard;
    }
}
