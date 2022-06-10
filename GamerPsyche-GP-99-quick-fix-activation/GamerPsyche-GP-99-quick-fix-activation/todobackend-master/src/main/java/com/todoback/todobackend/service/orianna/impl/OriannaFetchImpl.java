package com.todoback.todobackend.service.orianna.impl;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.datapipeline.common.HTTPClient;
import com.merakianalytics.orianna.types.common.Queue;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMasteries;
import com.merakianalytics.orianna.types.core.match.MatchHistory;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import com.todoback.todobackend.domain.User;
import com.todoback.todobackend.service.orianna.OriannaFetch;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;

@Service
public class OriannaFetchImpl implements OriannaFetch {
    @Override
    public String fetchBasicInfo(User user) {
        //System.out.println(Orianna.summonerNamed(user.getLOLUsername()).withRegion(user.getLolRegion()).get().getAccountId());
        Summoner summoner = Orianna.summonerNamed(user.getLOLUsername()).withRegion(user.getLolRegion()).get();
        System.out.println(summoner);
        //ChampionMasteries masteries = summoner.getChampionMasteries();
        //System.out.println(summoner.getCoreData());
        //System.out.println(summoner.getLevel());
        MatchHistory history = summoner.matchHistory().get();
        System.out.println(summoner.isInGame());
        System.out.println(history);

        //System.out.println(masteries);
        //System.out.println(matchHistory);
        //String lol = Orianna.summonerNamed(user.getLOLUsername()).withRegion(user.getLolRegion()).get().getAccountId();
        //System.out.println(lol);

        return "test";
    }



 }
