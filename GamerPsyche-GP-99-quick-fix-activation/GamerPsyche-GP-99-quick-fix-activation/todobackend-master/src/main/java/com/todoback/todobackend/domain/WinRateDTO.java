package com.todoback.todobackend.domain;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class WinRateDTO {
    String rankedSolo;
    String rankedFlex;
    String normalDraft;
    public String getRankedFlex() {
        return rankedFlex;
    }

    public void setRankedFlex(String rankedFlex) {
        this.rankedFlex = rankedFlex;
    }



    public String getNormalDraft() {
        return normalDraft;
    }

    public void setNormalDraft(String normalDraft) {
        this.normalDraft = normalDraft;
    }


    public void setRankedSolo(String rankedSolo) {
        this.rankedSolo = rankedSolo;
    }

    public String getRankedSolo() {
        return rankedSolo;
    }


}
