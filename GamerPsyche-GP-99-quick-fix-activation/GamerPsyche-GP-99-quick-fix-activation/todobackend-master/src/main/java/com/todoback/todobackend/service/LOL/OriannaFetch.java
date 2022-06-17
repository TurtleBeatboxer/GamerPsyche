package com.todoback.todobackend.service.LOL;

import com.merakianalytics.orianna.types.common.Queue;
import com.todoback.todobackend.domain.User;

public interface OriannaFetch {
    String fetchBasicInfo(User user);
    void getWinRateByQueue(Queue queue, User user);
}
