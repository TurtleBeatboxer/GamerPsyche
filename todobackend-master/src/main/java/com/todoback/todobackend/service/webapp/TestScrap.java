package com.todoback.todobackend.service.webapp;

import com.todoback.todobackend.domain.LOLUserDATA;
import com.todoback.todobackend.domain.RecentActivity;
import com.todoback.todobackend.domain.WinRateDTO;

import java.util.List;

public interface TestScrap {


    LOLUserDATA getLOLUserDATA(String lolServer, String lolUsername);
}
