package com.todoback.todobackend.domain;

public class RecentActivity {
    String date;
    String gamesRatio;

    String winRatio;
    String hoursPlayed;

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getGamesRatio() {
        return gamesRatio;
    }

    public void setGamesRatio(String gamesRatio) {
        this.gamesRatio = gamesRatio;
    }



    public String getWinRatio() {
        return winRatio;
    }

    public void setWinRatioByFloat(){
        //to trzeba zmienic i zmienic z tego jebanego stringa na float ale ja nie umiem xd
    }

    public void setWinRatioByString(String winRatio) {
        this.winRatio = winRatio;
    }

    public String getHoursPlayed() {
        return hoursPlayed;
    }

    public void setHoursPlayed(String hoursPlayed) {
        this.hoursPlayed = hoursPlayed;
    }
}
