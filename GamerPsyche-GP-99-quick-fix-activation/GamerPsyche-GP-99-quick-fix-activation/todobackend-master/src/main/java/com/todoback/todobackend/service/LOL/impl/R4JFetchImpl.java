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
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchTeam;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import no.stelar7.api.r4j.pojo.val.matchlist.MatchList;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class R4JFetchImpl implements R4JFetch {
    final R4J r4J = new R4J(APICredential.CRED);

    public void test(User user){
        Summoner summoner = SummonerAPI.getInstance().getSummonerByName(user.getLeagueShard(), user.getLOLUsername());
        MatchListBuilder builder = new MatchListBuilder();
        builder = builder.withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform());
        List<String> matchHistory = builder.withCount(21).get();
        System.out.println(matchHistory);
        MatchBuilder matchBuilder = new MatchBuilder(summoner.getPlatform());
        List<String> champions = new ArrayList<>();



        for(String m: matchHistory){
            LOLMatch match = matchBuilder.withId(m).getMatch();
            System.out.println(match.getGameMode());
            List<MatchParticipant> matchParticipants = match.getParticipants();
            for (int j = 0; j < match.getParticipants().size(); j++) {
                String Puuid = matchParticipants.get(j).getPuuid();
                if (Puuid.equals(summoner.getPUUID())) {
                    System.out.println(matchParticipants.get(j).getChampionName());

                        champions.add(matchParticipants.get(j).getChampionName());


                }
            }
        }

        List<String> championsV2 = new ArrayList<>(champions);
        Set<String> set = new HashSet<>(championsV2);
        championsV2.clear();
        championsV2.addAll(set);
        System.out.println(championsV2);
        System.out.println(champions);
        int count = 0;
        Map<String, Object> champs = new HashMap<>();
        for(String c: championsV2){
            count++;
            champs.put(c, Collections.frequency(champions, c));
            System.out.println(Collections.frequency(champions, c));
        }

        System.out.println(count);
        System.out.println(champs.get("Aphelios"));
       // int count = Collections.frequency(champions, "Aphelios");
       // System.out.println(count);
    }

    @Override
    public void R4JFetchBasicInfo(User user) {

        Summoner summoner = SummonerAPI.getInstance().getSummonerByName(user.getLeagueShard(), user.getLOLUsername());
        MatchListBuilder builder = new MatchListBuilder();
        builder = builder.withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform());


        MatchBuilder matchBuilder = new MatchBuilder(summoner.getPlatform());
        List<String> solo = builder.withQueue(GameQueueType.TEAM_BUILDER_RANKED_SOLO).withCount(100).get();

        LOLMatch match = matchBuilder.withId(solo.get(0)).getMatch();
        List<MatchTeam> teams = match.getTeams();
        System.out.println(teams.get(0).didWin());
        System.out.println(teams.get(1).didWin());
        System.out.println(match.getParticipants());

    }

    public float R4JFetchWinRateByQueue(User user, GameQueueType queueType) {
        Summoner summoner = SummonerAPI.getInstance().getSummonerByName(user.getLeagueShard(), user.getLOLUsername());
        MatchListBuilder builder = new MatchListBuilder();
        builder = builder.withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform());
        MatchBuilder matchBuilder = new MatchBuilder(summoner.getPlatform());
        List<String> solo = builder.withQueue(queueType).withCount(100).get();
        float wins = 0;
        float loses = 0;


        for (String s : solo) {
            LOLMatch match = matchBuilder.withId(s).getMatch();
            if (match.getGameStartTimestamp() > 1641513601000L) {


                System.out.println(match.getGameEndAsDate());
                List<MatchParticipant> matchParticipants = match.getParticipants();
                for (int j = 0; j < match.getParticipants().size(); j++) {
                    String Puuid = matchParticipants.get(j).getPuuid();
                    if (Puuid.equals(summoner.getPUUID())) {
                        if (matchParticipants.get(j).didWin()) {
                            System.out.println("win");
                            wins++;
                        } else {
                            System.out.println("lose");
                            loses++;
                        }
                        break;
                    }
                }
            }
        }
        return calculateWinRate(wins, loses);
    }

    public float calculateWinRate(float wins, float loses) {
        float allGames = wins + loses;
        return wins / allGames;
    }
}
