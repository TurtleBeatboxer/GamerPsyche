package com.todoback.todobackend.domain;

public class ChampionMatchHistoryData {
    private int crowdControlScore;
    private int creepScorePM;
    private int visionScorePM;
    private int damagePM;
    private int selfMitigatedPM;
    private int KDA;
    private int KP;
    private int objectivesTaken;

    public int getCrowdControlScore() {
        return crowdControlScore;
    }

    public void setCrowdControlScore(int crowdControlScore) {
        this.crowdControlScore = crowdControlScore;
    }

    public int getCreepScorePM() {
        return creepScorePM;
    }

    public void setCreepScorePM(int creepScorePM) {
        this.creepScorePM = creepScorePM;
    }

    public int getVisionScorePM() {
        return visionScorePM;
    }

    public void setVisionScorePM(int visionScorePM) {
        this.visionScorePM = visionScorePM;
    }

    public int getDamagePM() {
        return damagePM;
    }

    public void setDamagePM(int damagePM) {
        this.damagePM = damagePM;
    }

    public int getSelfMitigatedPM() {
        return selfMitigatedPM;
    }

    public void setSelfMitigatedPM(int selfMitigatedPM) {
        this.selfMitigatedPM = selfMitigatedPM;
    }

    public int getKDA() {
        return KDA;
    }

    public void setKDA(int KDA) {
        this.KDA = KDA;
    }

    public int getKP() {
        return KP;
    }

    public void setKP(int KP) {
        this.KP = KP;
    }

    public int getObjectivesTaken() {
        return objectivesTaken;
    }

    public void setObjectivesTaken(int objectivesTaken) {
        this.objectivesTaken = objectivesTaken;
    }

}
