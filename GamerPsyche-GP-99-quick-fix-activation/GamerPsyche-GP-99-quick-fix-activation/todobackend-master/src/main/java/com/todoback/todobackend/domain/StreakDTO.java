package com.todoback.todobackend.domain;

import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;

import java.util.ArrayList;
import java.util.List;

public class StreakDTO {

    public StreakDTO(List<LOLMatch> StreakMatches, StreakType streakType, String puuid) {
        this.postStreak = StreakMatches;
        this.streakType = streakType;
        this.puuid = puuid;
    }

    private StreakType streakType;
    private List<LOLMatch> postStreak = new ArrayList<>();
    private String puuid;

    public StreakType getStreakType() {
        return streakType;
    }

    public void setStreakType(StreakType streakType) {
        this.streakType = streakType;
    }

    public List<LOLMatch> getPostStreak() {
        return postStreak;
    }

    public void setPostStreak(List<LOLMatch> postStreak) {
        this.postStreak = postStreak;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }
}