package com.todoback.todobackend.service.LOL;

import com.merakianalytics.orianna.types.common.Queue;
import com.todoback.todobackend.domain.User;

public interface OriannaFetch {
    void test();
    String fetchBasicInfo(User user);
    float getWinRateByQueue(Queue queue, User user) throws Exception;
}
