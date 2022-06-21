package com.todoback.todobackend.service.LOL;

import com.todoback.todobackend.configuration.APICredential;
import com.todoback.todobackend.domain.User;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.impl.R4J;

public interface R4JFetch {
    void R4JFetchBasicInfo(User user);
    float R4JFetchWinRateByQueue(User user, GameQueueType queueType);
    void test(User user);
}
