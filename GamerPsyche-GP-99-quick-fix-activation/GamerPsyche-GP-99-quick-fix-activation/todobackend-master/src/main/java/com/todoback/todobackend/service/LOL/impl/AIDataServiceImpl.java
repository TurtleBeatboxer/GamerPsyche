package com.todoback.todobackend.service.LOL.impl;

import com.todoback.todobackend.domain.AIEndPointData;
import com.todoback.todobackend.domain.ChampionMatchHistoryData;
import com.todoback.todobackend.domain.User;
import com.todoback.todobackend.service.LOL.AIDataService;
import com.todoback.todobackend.repository.UserRepository;
import com.todoback.todobackend.service.LOL.HelperService;
import com.todoback.todobackend.service.LOL.JsonConverter;
import com.todoback.todobackend.service.LOL.R4JFetch;
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchBuilder;
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchListBuilder;
import no.stelar7.api.r4j.impl.lol.raw.SummonerAPI;
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchTeam;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class AIDataServiceImpl implements AIDataService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private R4JFetch r4JFetch;
    @Autowired
    private HelperService helperService;
    @Autowired
    JsonConverter jsonConverter;
    public void userSearch(String lolUsername, int championId){
        System.out.println("Service");
        Optional<User> userOptional = userRepository.findByLolUsername(lolUsername);

        userOptional.ifPresent(user -> {
            try {
                System.out.println("Present");
                checkSearchType(user, championId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }



    public List<ChampionMatchHistoryData> checkSearchType(User user, int championId) throws Exception {
        List<ChampionMatchHistoryData> championMatchHistoryData = new ArrayList<>();
        Summoner summoner = SummonerAPI.getInstance().getSummonerByName(user.getLeagueShard(), user.getLOLUsername());
        MatchListBuilder builder = new MatchListBuilder().withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform());
        MatchBuilder matchBuilder = new MatchBuilder(summoner.getPlatform());
        ArrayList<Integer> queues = helperService.queueIds();
        for (int i = 0; i < 3; i++) {
            List<String> solo = builder.withQueue(helperService.gameQueueTypePresent(i, queues)).withCount(25).get();
            for (String s : solo) {
                LOLMatch match = matchBuilder.withId(s).getMatch();
                if (match.getGameStartTimestamp() > 1641513601000L) {
                    System.out.println(match + "test");
                    if(championId < 0){
                        findMatchHistory(championMatchHistoryData, summoner, match);
                    }else {
                        findMatchHistoryByChampion(championId, championMatchHistoryData, summoner, match);
                    }


                }
            }
        }
        jsonConverter.convertChampionMatchHistoryDataToJSON(championMatchHistoryData);
        return championMatchHistoryData;
    }
    public void findMatchHistory(List<ChampionMatchHistoryData> championMatchHistoryData, Summoner summoner, LOLMatch match) {

        for (int j = 0; j < match.getParticipants().size(); j++) {
            String PUUID = match.getParticipants().get(j).getPuuid();
            if (PUUID.equals(summoner.getPUUID())) {
                MatchParticipant matchParticipant = match.getParticipants().get(j);
                double gameTime = helperService.calculateGameTime(match.getGameDuration());
                MatchTeam matchTeam = helperService.getUserTeam(match, matchParticipant);
                    ChampionMatchHistoryData data = new ChampionMatchHistoryData();
                    helperService.setData(matchParticipant, matchTeam, gameTime, data);
                    championMatchHistoryData.add(data);
                break;
            }
        }
    }

    private void findMatchHistoryByChampion(int championId, List<ChampionMatchHistoryData> championMatchHistoryData, Summoner summoner, LOLMatch match){
        for (int j = 0; j < match.getParticipants().size(); j++) {
            String PUUID = match.getParticipants().get(j).getPuuid();
            if (PUUID.equals(summoner.getPUUID())) {
                MatchParticipant matchParticipant = match.getParticipants().get(j);
                double gameTime = helperService.calculateGameTime(match.getGameDuration());
                MatchTeam matchTeam = helperService.getUserTeam(match, matchParticipant);
                if (match.getParticipants().get(j).getChampionId() == championId) {
                    ChampionMatchHistoryData data = new ChampionMatchHistoryData();
                    helperService.setData(matchParticipant, matchTeam, gameTime, data);
                    championMatchHistoryData.add(data);
                }
                break;
            }
        }
    }







}
