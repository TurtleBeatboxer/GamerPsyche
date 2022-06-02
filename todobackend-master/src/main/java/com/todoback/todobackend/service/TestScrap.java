package com.todoback.todobackend.service;

import com.todoback.todobackend.domain.WinRateDTO;

public interface TestScrap {
    void scrapData();

    WinRateDTO scrapWinRate();
}
