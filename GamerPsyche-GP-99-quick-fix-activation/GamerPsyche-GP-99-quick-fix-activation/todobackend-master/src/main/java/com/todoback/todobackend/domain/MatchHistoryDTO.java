package com.todoback.todobackend.domain;

public class MatchHistoryDTO {
    private String championName;
    private boolean didWin;
    private int kills;
    private int assists;
    private int deaths;
    private String summonerName;

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public boolean isDidWin() {
        return didWin;
    }

    public void setDidWin(boolean didWin) {
        this.didWin = didWin;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kill) {
        this.kills = kill;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }




}
