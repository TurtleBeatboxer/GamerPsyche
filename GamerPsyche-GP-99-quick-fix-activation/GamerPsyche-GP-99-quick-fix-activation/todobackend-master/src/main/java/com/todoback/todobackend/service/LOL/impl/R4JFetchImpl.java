package com.todoback.todobackend.service.LOL.impl;

import com.merakianalytics.orianna.types.core.match.Match;
import com.merakianalytics.orianna.types.core.match.MatchHistory;
import com.todoback.todobackend.configuration.APICredential;
import com.todoback.todobackend.domain.User;
import com.todoback.todobackend.service.LOL.R4JFetch;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchBuilder;
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchListBuilder;
import no.stelar7.api.r4j.impl.lol.raw.SummonerAPI;
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchTeam;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import no.stelar7.api.r4j.pojo.val.matchlist.MatchList;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Queue;

@Service
public class R4JFetchImpl implements R4JFetch {
    final R4J r4J = new R4J(APICredential.CRED);

    @Override
    public void R4JFetchBasicInfo(User user){
        /*System.out.println(user.getLeagueShard());
        System.out.println(user.getLOLUsername());*/
       /* List<String> matchHistory = SummonerAPI.getInstance().getSummonerByName(user.getLeagueShard(),user.getLOLUsername()).getLeagueGames().get();*/
        Summoner summoner = SummonerAPI.getInstance().getSummonerByName(user.getLeagueShard(), "TurtleBB");
        MatchListBuilder builder = new MatchListBuilder();
        builder = builder.withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform());
      /*  List<String> string1 = builder.withQueue(GameQueueTyp).withCount(10).get();
        List<String> string2 = builder.withQueue(GameQueueType.RANKED_FLEX_SR).withCount(10).get();
        List<String> string3 = builder.withQueue(GameQueueType.TEAM_BUILDER_RANKED_SOLO).withCount(10).get();
        List<String> string4 = builder.withQueue(GameQueueType.TEAM_BUILDER_DRAFT_RANKED_5X5).withCount(10).get();
        List<String> string5 = builder.withQueue(GameQueueType.TEAM_BUILDER_DRAFT_RANKED_5X5).withCount(10).get();
        List<String> string6 = builder.withQueue(GameQueueType.TEAM_BUILDER_DRAFT_RANKED_5X5).withCount(10).get();*/
        MatchBuilder matchBuilder = new MatchBuilder(summoner.getPlatform());
        List<String> solo = builder.withQueue(GameQueueType.TEAM_BUILDER_RANKED_SOLO).withCount(100).get();
       // for(int i = 0; i < solo.size(); i++){
            LOLMatch match = matchBuilder.withId(solo.get(0)).getMatch();
            List<MatchTeam> teams = match.getTeams();
            System.out.println(teams.get(0).didWin());
        System.out.println(teams.get(1).didWin());
            System.out.println(match.getParticipants());
      //  }

        /*System.out.println(builder.withQueue(GameQueueType.RANKED_FLEX_SR).get().size());
        System.out.println(builder.withQueue(GameQueueType.TEAM_BUILDER_DRAFT_UNRANKED_5X5).get().size());
        System.out.println(summoner.getPUUID());*/


      /*  for(int i = 0; i < 20; i++){
            System.out.println(matchHistory.get(0));
            String str = matchHistory.get(0);
            MatchBuilder match = new MatchBuilder(user.getLeagueShard(), str);
            LOLMatch ma = match.getMatch();
            System.out.println(ma);


        }
        System.out.println(matchHistory.size());
        System.out.println("test");*/
    }
}
