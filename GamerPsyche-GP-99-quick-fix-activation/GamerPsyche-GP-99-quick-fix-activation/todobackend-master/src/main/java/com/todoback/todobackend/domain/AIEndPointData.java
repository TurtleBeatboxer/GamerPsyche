package com.todoback.todobackend.domain;

public class AIEndPointData {
    private User user;
    private int championId;

    public AIEndPointData(User user, int championId) {
        this.user = user;
        this.championId = championId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }
}
