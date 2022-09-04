package com.todoback.todobackend.service.LOL.impl;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Queue;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.league.League;
import com.merakianalytics.orianna.types.core.league.LeagueEntry;
import com.merakianalytics.orianna.types.core.spectator.CurrentMatch;
import com.merakianalytics.orianna.types.core.staticdata.Champion;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import com.todoback.todobackend.configuration.APICredential;
import com.todoback.todobackend.domain.User;
import com.todoback.todobackend.service.LOL.OriannaFetch;
import no.stelar7.api.r4j.impl.R4J;
import org.springframework.stereotype.Service;

@Service
public class OriannaFetchImpl implements OriannaFetch {
    final R4J r4J = new R4J(APICredential.CRED);

    public float getWinRateByQueue(Queue queue, User user) throws Exception {
        Summoner summoner = Orianna.summonerNamed(user.getLOLUsername()).withRegion(user.getLolRegion()).get();
        LeagueEntry entry = summoner.getLeaguePosition(queue);
        if (entry == null) {
           throw new Exception("data null");
       }
        float winRate = calculateWinRate(entry.getWins(), entry.getLosses());
        return Math.round(winRate * 100);
    }

    public float calculateWinRate(float wins, float loses) {
        float allGames = wins + loses;
        return wins / allGames;
    }
}
