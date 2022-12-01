package com.todoback.todobackend.service.LOL.impl;

import com.todoback.todobackend.domain.ChampionMatchHistoryData;
import com.todoback.todobackend.domain.LOLServer;
import com.todoback.todobackend.service.LOL.*;
import com.todoback.todobackend.repository.UserRepository;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.basic.constants.types.lol.LaneType;
import no.stelar7.api.r4j.basic.constants.types.lol.TeamType;
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchBuilder;
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchListBuilder;
import no.stelar7.api.r4j.impl.lol.raw.SummonerAPI;
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchTeam;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
    OriannaUsagePreparationService oriannaUsagePreparationService = new OriannaUsagePreparationService();


    public void userSearchBrute(String lolUsername) throws Exception {
        gatherJSONdata(brutForceShard(lolUsername));
    }

    public Summoner brutForceShard(String lolUsername){
        List<LOLServer> list = Arrays.asList(LOLServer.BR, LOLServer.EUNE, LOLServer.EUW, LOLServer.LAN, LOLServer.LAS, LOLServer.NA, LOLServer.OCE, LOLServer.RU, LOLServer.TR, LOLServer.JP, LOLServer.KR);
        for (LOLServer lolServer : list) {
            Optional<Summoner> summoner = Optional.ofNullable(SummonerAPI.getInstance().getSummonerByName(oriannaUsagePreparationService.translateEnumServerToRiotRegionR4J(lolServer), lolUsername));

            if(summoner.isPresent()){
                System.out.println(summoner.get().getPlatform());
                return summoner.get();
            }
        }
        return null;
    }



    public void gatherJSONdata(Summoner summoner) throws Exception {
        List<List<ChampionMatchHistoryData>> data = new ArrayList<>();
        ArrayList<Integer> queues = helperService.queueIds(true, false, false);
        System.out.println(queues);
        for (Integer queue : queues) {
            List<String> matchList = new MatchListBuilder().withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform()).withQueue(helperService.gameQueueTypePresent(queue)).withCount(3).get();
            System.out.println(matchList.size());
            for (String  m: matchList){

                LOLMatch match = new MatchBuilder().withPlatform(RegionShard.EUROPE).withId(m).getMatch();
                Thread.sleep(1000);
                List<ChampionMatchHistoryData> matchData = new ArrayList<>();


                TeamType myTeam = null;
                if (match.getGameStartTimestamp() > 1641513601000L){
                    for(MatchParticipant mpa : match.getParticipants()){
                        if(mpa.getPuuid().equals(summoner.getPUUID())){

                            myTeam = mpa.getTeam();
                        }
                    }
                    for(MatchParticipant mp : match.getParticipants()){
                        if(mp.getTeam() == myTeam) {
                            ChampionMatchHistoryData championMatchHistoryData = new ChampionMatchHistoryData();

                            championMatchHistoryData.setWinRateOnPlayedRole(getWinRateByRole(mp.getSummonerName(), summoner.getPlatform(), mp.getChampionSelectLane()));
                            championMatchHistoryData.setWinRateOnPlayedChampion(getWinRateGeneral(mp.getSummonerName(), summoner.getPlatform(), mp.getChampionId()));
                            findMatchHistory(championMatchHistoryData, summoner, mp, match);
                            matchData.add(championMatchHistoryData);
                            System.out.println(championMatchHistoryData.getUsername());
                        }
                    }
                }
                data.add(matchData);
            }
        }
        jsonConverter.convertChampionMatchHistoryDataToJSON(data);
    }

    public int getWinRateByRole(String summonerName, LeagueShard leagueShard, LaneType laneType) throws InterruptedException {
        Summoner summoner = SummonerAPI.getInstance().getSummonerByName(leagueShard, summonerName);


        return getWinRateRole(summoner, laneType);
    }

    private int getWinRateRole(Summoner summoner, LaneType laneType) throws InterruptedException {
       /* float wins = 0;
        float loses = 0;
                        if(matchParticipant.getPuuid().equals(summoner.getPUUID())) {
                            if (matchParticipant.getChampionSelectLane() == laneType) {
                                if (matchParticipant.didWin()) {
                                    System.out.println(wins);
                                    System.out.println(matchParticipant.getChampionName());
                                    wins++;
                                } else {
                                    loses++;
                                }
                                if ((wins + loses) >= 20) {
                                    return Math.round(calculateWinRate(wins, loses) * 100);
                                }
                            }
                        }
        System.out.println(wins + " " + loses);
        return Math.round(calculateWinRate(wins, loses)*100);*/
        ArrayList<Integer> queues = helperService.queueIds(true, false, false);
        float wins = 0;
        float loses = 0;
        for (Integer queue : queues) {
            List<String> matchList = new MatchListBuilder().withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform()).withQueue(helperService.gameQueueTypePresent(queue)).withCount(40).get();
            System.out.println(helperService.gameQueueTypePresent(queue));
            System.out.println(matchList.size());
            for (String  m: matchList) {
                LOLMatch match = new MatchBuilder().withPlatform(RegionShard.EUROPE).withId(m).getMatch();
                Thread.sleep(350);
                if(match == null){
                    continue;
                }
                if (match.getGameStartTimestamp() > 1641513601000L) {
                    List<MatchParticipant> matchParticipants = match.getParticipants();
                    for (int j = 0; j < match.getParticipants().size(); j++) {
                        String Puuid = matchParticipants.get(j).getPuuid();
                        if (Puuid.equals(summoner.getPUUID())) {
                            if (matchParticipants.get(j).getLane() == laneType) {

                                if (matchParticipants.get(j).didWin()) {
                                    wins++;
                                } else {
                                    loses++;
                                }
                                if ((wins + loses) >= 10) {
                                    return Math.round(calculateWinRate(wins, loses) * 100);
                                }
                                break;
                            }
                        }
                        //  System.out.println(matchParticipants.get(j).didWin());


                    }
                }
            }
        }
        return Math.round(calculateWinRate(wins, loses)*100);
    }




    public int getWinRateGeneral(String summonerName, LeagueShard leagueShard, int championId) throws InterruptedException {
        Summoner summoner = SummonerAPI.getInstance().getSummonerByName(leagueShard, summonerName);
        return getWinRate(summoner, championId);
    }

    private int getWinRate(Summoner summoner,   int championId) throws InterruptedException {
        ArrayList<Integer> queues = helperService.queueIds(true, false, false);
        float wins = 0;
        float loses = 0;
        for (Integer queue : queues) {
            List<String> matchList = new MatchListBuilder().withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform()).withQueue(helperService.gameQueueTypePresent(queue)).withCount(20).get();
            for (String  m: matchList) {
                LOLMatch match = new MatchBuilder().withPlatform(RegionShard.EUROPE).withId(m).getMatch();
                Thread.sleep(350);
                if (match.getGameStartTimestamp() > 1641513601000L) {
                    List<MatchParticipant> matchParticipants = match.getParticipants();
                    for (int j = 0; j < match.getParticipants().size(); j++) {
                        String Puuid = matchParticipants.get(j).getPuuid();
                        if (Puuid.equals(summoner.getPUUID())) {

                            if (matchParticipants.get(j).getChampionId() == championId) {
                                if (matchParticipants.get(j).didWin()) {
                                    wins++;
                                } else {
                                    loses++;
                                }
                                if((wins + loses) >= 10){
                                    return Math.round(calculateWinRate(wins, loses)*100);
                                }
                                break;
                            }
                        }

                        //  System.out.println(matchParticipants.get(j).didWin());


                    }
                }
            }
        }
        return Math.round(calculateWinRate(wins, loses)*100);
    }


    public void findMatchHistory(ChampionMatchHistoryData championMatchHistoryData, Summoner summoner, MatchParticipant mp, LOLMatch lolMatch) throws InterruptedException {




        double gameTime = helperService.calculateGameTime(lolMatch.getGameDuration());
        MatchTeam matchTeam = helperService.getUserTeam(lolMatch, mp);
        if (mp.getPuuid().equals(summoner.getPUUID())) {
            helperService.setData(mp, matchTeam, gameTime, championMatchHistoryData, true);
        }
        helperService.setData(mp, matchTeam, gameTime, championMatchHistoryData, false);


    }



    public float calculateWinRate(float wins, float loses) {
        float allGames = wins + loses;
        return wins / allGames;
    }




    public void python() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("python", resolvePythonScriptPath("C:\\Users\\pkury\\Desktop\\python\\main.py"));
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();



    }

    private String resolvePythonScriptPath(String path){
        File file = new File(path);
        return file.getAbsolutePath();
    }


/*

    public List<List<ChampionMatchHistoryData>> checkSearchType(Summoner summ, int championId) throws Exception {
        List<List<ChampionMatchHistoryData>> championMatchHistoryData = new ArrayList<>();
        Summoner summoner = SummonerAPI.getInstance().getSummonerByName(summ.getPlatform(), summ.getName());
        MatchListBuilder builder = new MatchListBuilder().withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform());
        MatchBuilder matchBuilder = new MatchBuilder(summoner.getPlatform());
        ArrayList<Integer> queues = helperService.queueIds(true, false, false);
        for (int i = 0; i < 3; i++) {
            List<String> solo = builder.withQueue(helperService.gameQueueTypePresent(queues.get(i))).withCount(10).get();
            for (String s : solo) {

                LOLMatch match = matchBuilder.withPlatform(RegionShard.EUROPE).withId(s).getMatch();
               Thread.sleep(300);
                if (match.getGameStartTimestamp() > 1641513601000L) {
                    //System.out.println(match + "test");
                    if(championId < 0){
                       // findMatchHistory(championMatchHistoryData, summoner, match);
                    }else {
                        findMatchHistoryByChampion(championId, championMatchHistoryData, summoner, match);
                    }


                }
            }
        }
        jsonConverter.convertChampionMatchHistoryDataToJSON(championMatchHistoryData);
        return championMatchHistoryData;
    }


    private void findMatchHistoryByChampion(int championId, List<List<ChampionMatchHistoryData>> championMatchHistoryData, Summoner summoner, LOLMatch match){
        List<ChampionMatchHistoryData> matchData = new ArrayList<>();
        for (int j = 0; j < match.getParticipants().size(); j++) {
            String PUUID = match.getParticipants().get(j).getPuuid();
            MatchParticipant matchParticipant = match.getParticipants().get(j);
            double gameTime = helperService.calculateGameTime(match.getGameDuration());
            MatchTeam matchTeam = helperService.getUserTeam(match, matchParticipant);
            if (PUUID.equals(summoner.getPUUID())) {
                if (match.getParticipants().get(j).getChampionId() != championId) {

                    return;
                }

                    ChampionMatchHistoryData data = new ChampionMatchHistoryData();
                    helperService.setData(matchParticipant, matchTeam, gameTime, data, true);
                    matchData.add(data);
                    return;

            }
            ChampionMatchHistoryData data = new ChampionMatchHistoryData();
            helperService.setData(matchParticipant, matchTeam, gameTime, data, false);
            matchData.add(data);

        }

            championMatchHistoryData.add(matchData);


    }

*/





















































}
