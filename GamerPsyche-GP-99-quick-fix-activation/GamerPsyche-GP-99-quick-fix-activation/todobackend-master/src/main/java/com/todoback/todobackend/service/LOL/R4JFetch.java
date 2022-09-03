package com.todoback.todobackend.service.LOL;

import com.todoback.todobackend.domain.ChampionMatchHistoryData;
import com.todoback.todobackend.domain.MatchHistoryDTO;
import com.todoback.todobackend.domain.User;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;

import java.util.List;
import java.util.Map;

public interface R4JFetch {
    void test();
    List<ChampionMatchHistoryData> getDataFromUserMatch(int championId, String summonerName, LeagueShard server) throws Exception;
    Map<String, Integer>getMostPlayedChampions(String username);
    float R4JFetchWinRateByQueue(User user, GameQueueType queueType);
    Map<String, Integer> fetchMostPlayedChampions(User user);
    List<MatchHistoryDTO> getMatchHistory(User user);
}
