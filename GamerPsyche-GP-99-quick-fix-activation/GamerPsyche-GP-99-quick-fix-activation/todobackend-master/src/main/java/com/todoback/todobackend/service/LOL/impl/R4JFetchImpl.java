package com.todoback.todobackend.service.LOL.impl;

import com.todoback.todobackend.configuration.APICredential;
import com.todoback.todobackend.domain.User;
import com.todoback.todobackend.service.LOL.R4JFetch;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.impl.lol.raw.SummonerAPI;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class R4JFetchImpl implements R4JFetch {
    final R4J r4J = new R4J(APICredential.CRED);

    @Override
    public void R4JFetchBasicInfo(User user){
        List<String> matchHistory = SummonerAPI.getInstance().getSummonerByName(user.getLeagueShard(),user.getLolUsername()).getLeagueGames().get();
        System.out.println("test");
    }
}
