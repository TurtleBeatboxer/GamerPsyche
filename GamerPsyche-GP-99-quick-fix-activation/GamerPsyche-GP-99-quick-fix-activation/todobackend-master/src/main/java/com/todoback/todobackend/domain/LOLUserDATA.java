package com.todoback.todobackend.domain;
import java.util.List;

public class LOLUserDATA {
    private WinRateDTO userWinrate;
    private List<RecentActivity> activityList;

    public WinRateDTO getUserWinrate() {
        return userWinrate;
    }

    public void setUserWinrate(WinRateDTO userWinrate) {
        this.userWinrate = userWinrate;
    }

    public List<RecentActivity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<RecentActivity> activityList) {
        this.activityList = activityList;
    }

}
