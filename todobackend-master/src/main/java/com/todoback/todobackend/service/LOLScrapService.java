package com.todoback.todobackend.service;


import com.todoback.todobackend.domain.WinRateDTO;

public interface LOLScrapService {
    void scrapData();
    WinRateDTO scrapWinRate();
}
