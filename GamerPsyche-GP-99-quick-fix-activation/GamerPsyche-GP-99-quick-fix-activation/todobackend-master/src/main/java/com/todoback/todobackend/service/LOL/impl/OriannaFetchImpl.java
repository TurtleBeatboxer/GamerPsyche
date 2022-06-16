package com.todoback.todobackend.service.LOL.impl;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.core.match.MatchHistory;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import com.todoback.todobackend.configuration.APICredential;
import com.todoback.todobackend.domain.User;
import com.todoback.todobackend.service.LOL.OriannaFetch;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.impl.lol.raw.SummonerAPI;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class OriannaFetchImpl implements OriannaFetch {
    final R4J r4J = new R4J(APICredential.CRED);

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
