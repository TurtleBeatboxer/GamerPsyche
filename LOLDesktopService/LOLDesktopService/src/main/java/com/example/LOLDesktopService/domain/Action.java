package com.example.LOLDesktopService.domain;

public class Action {
    private int actorCellId;
    private int championId;
    private boolean completed;
    private int id;
    private boolean isAllyAction;
    private boolean isInProgress;
    private int pickTurn;
    private String type;
    private String SummonerName;
    private String position;

    public String getSummonerName() {
        return SummonerName;
    }

    public void setSummonerName(String summonerName) {
        SummonerName = summonerName;
    }



    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }




    public int getActorCellId() {
        return actorCellId;
    }

    public void setActorCellId(int actorCellId) {
        this.actorCellId = actorCellId;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAllyAction() {
        return isAllyAction;
    }

    public void setAllyAction(boolean allyAction) {
        isAllyAction = allyAction;
    }

    public boolean isInProgress() {
        return isInProgress;
    }

    public void setInProgress(boolean inProgress) {
        isInProgress = inProgress;
    }

    public int getPickTurn() {
        return pickTurn;
    }

    public void setPickTurn(int pickTurn) {
        this.pickTurn = pickTurn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
