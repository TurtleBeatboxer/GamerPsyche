package com.todoback.todobackend.service.LOL;

import com.todoback.todobackend.configuration.APICredential;
import com.todoback.todobackend.domain.MatchHistoryDTO;
import com.todoback.todobackend.domain.User;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant;

import java.util.List;
import java.util.Map;

public interface R4JFetch {
    void R4JFetchBasicInfo(User user);
    float R4JFetchWinRateByQueue(User user, GameQueueType queueType);
    Map<String, Integer> getMostPlayedChampions(User user);
    List<MatchHistoryDTO> getMatchHistory(User user);
}
