package com.todoback.todobackend.domain;

public class ChampionMatchHistoryData {


    private String username;
    private boolean searchedUser;
    private String champion;
    private boolean didWon;
    private double crowdControlScore;
    private double creepScorePM;
    private double visionScorePM;
    private double damagePM;
    private double selfMitigatedPM;
    private double KDA;
    private double KP;
    private ObjectiveMatchHistoryData objectivesTaken;

    public void setChampionMatchHistoryData(String champion, boolean didWon, boolean searchedUser, String username, double crowdControlScore, double creepScorePM, double visionScorePM, double damagePM, double selfMitigatedPM, double KDA, double KP, ObjectiveMatchHistoryData objectivesTaken){
        this.didWon = didWon;
        this.champion = champion;
        this.searchedUser = searchedUser;
        this.username = username;
        this.creepScorePM = creepScorePM;
        this.crowdControlScore = crowdControlScore;
        this.visionScorePM = visionScorePM;
        this.damagePM = damagePM;
        this.selfMitigatedPM = selfMitigatedPM;
        this.KDA = KDA;
        this.KP = KP;
        this.objectivesTaken = objectivesTaken;
    }

    public String getChampion() {
        return champion;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }

    public boolean isSearchedUser() {
        return searchedUser;
    }

    public void setSearchedUser(boolean searchedUser) {
        this.searchedUser = searchedUser;
    }

    public boolean isDidWon() {
        return didWon;
    }

    public void setDidWon(boolean didWon) {
        this.didWon = didWon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getCrowdControlScore() {
        return crowdControlScore;
    }

    public void setCrowdControlScore(double crowdControlScore) {
        this.crowdControlScore = crowdControlScore;
    }

    public double getCreepScorePM() {
        return creepScorePM;
    }

    public void setCreepScorePM(double creepScorePM) {
        this.creepScorePM = creepScorePM;
    }

    public double getVisionScorePM() {
        return visionScorePM;
    }

    public void setVisionScorePM(double visionScorePM) {
        this.visionScorePM = visionScorePM;
    }

    public double getDamagePM() {
        return damagePM;
    }

    public void setDamagePM(double damagePM) {
        this.damagePM = damagePM;
    }

    public double getSelfMitigatedPM() {
        return selfMitigatedPM;
    }

    public void setSelfMitigatedPM(double selfMitigatedPM) {
        this.selfMitigatedPM = selfMitigatedPM;
    }

    public double getKDA() {
        return KDA;
    }

    public void setKDA(double KDA) {
        this.KDA = KDA;
    }

    public double getKP() {
        return KP;
    }

    public void setKP(double KP) {
        this.KP = KP;
    }

    public ObjectiveMatchHistoryData getObjectivesTaken() {
        return objectivesTaken;
    }

    public void setObjectivesTaken(ObjectiveMatchHistoryData objectivesTaken) {
        this.objectivesTaken = objectivesTaken;
    }

}
