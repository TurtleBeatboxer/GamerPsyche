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

    public void test(){
        Summoner summoner = Orianna.summonerNamed("koczokok").withRegion(Region.EUROPE_NORTH_EAST).get();
        System.out.println(summoner.isInGame());
        CurrentMatch match = Orianna.currentMatchForSummoner(summoner).get();
        System.out.println(match);
    }

    public String fetchBasicInfo(User user) {
        Summoner summoner = Orianna.summonerNamed(user.getLOLUsername()).withRegion(user.getLolRegion()).get();
        System.out.println(summoner);
        League leauge = summoner.getLeague(Queue.RANKED_FLEX);
        LeagueEntry entry = summoner.getLeaguePosition(Queue.RANKED_FLEX);
        float games = entry.getWins() + entry.getLosses();
        System.out.println(games);
        float wins = entry.getWins();
        float winrate = wins / games;
        System.out.println(winrate);
        System.out.println(entry.getWins());
        return "test";
    }

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

      public String championName(int id){
        System.out.println(id);
        if(id > 0){
            System.out.println("in if");
            Champion champion = Orianna.championWithId(id).withRegion(Region.EUROPE_NORTH_EAST).get();
            return champion.getName();
        }
       return "id equals 0";
    }


}
