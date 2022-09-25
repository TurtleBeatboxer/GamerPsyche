package com.todoback.todobackend.service.LOL;

import com.todoback.todobackend.domain.ChampionMatchHistoryData;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchTeam;

import java.util.ArrayList;

public interface HelperService {
    float fetchWinRateByQueue(String username, int queue);
    ArrayList<Integer> queueIds();
    GameQueueType gameQueueTypePresent(int i, ArrayList<Integer> queues);
    double calculateGameTime(double gameTimeInSeconds);
    void setData(MatchParticipant matchParticipant, MatchTeam matchTeam, double gameTime, ChampionMatchHistoryData data);
    MatchTeam getUserTeam(LOLMatch match, MatchParticipant matchParticipant);
}
