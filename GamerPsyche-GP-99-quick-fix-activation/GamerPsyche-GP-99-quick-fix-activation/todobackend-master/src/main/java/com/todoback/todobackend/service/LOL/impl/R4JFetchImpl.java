package com.todoback.todobackend.service.LOL.impl;

import com.todoback.todobackend.configuration.APICredential;
import com.todoback.todobackend.domain.ChampionMatchHistoryData;
import com.todoback.todobackend.domain.ObjectiveMatchHistoryData;
import com.todoback.todobackend.domain.User;
import com.todoback.todobackend.domain.MatchHistoryDTO;
import com.todoback.todobackend.repository.UserRepository;
import com.todoback.todobackend.service.LOL.JsonConverter;
import com.todoback.todobackend.service.LOL.R4JFetch;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.basic.constants.types.lol.TeamType;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchBuilder;
import no.stelar7.api.r4j.impl.lol.builders.matchv5.match.MatchListBuilder;
import no.stelar7.api.r4j.impl.lol.lcu.LCUApi;
import no.stelar7.api.r4j.impl.lol.liveclient.LiveClientDataAPI;
import no.stelar7.api.r4j.impl.lol.raw.SpectatorAPI;
import no.stelar7.api.r4j.impl.lol.raw.SummonerAPI;
import no.stelar7.api.r4j.pojo.lol.liveclient.ActiveGameData;
import no.stelar7.api.r4j.pojo.lol.liveclient.events.GameEvent;
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchTeam;
import no.stelar7.api.r4j.pojo.lol.match.v5.ObjectiveStats;
import no.stelar7.api.r4j.pojo.lol.spectator.SpectatorGameInfo;
import no.stelar7.api.r4j.pojo.lol.spectator.SpectatorParticipant;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import com.todoback.todobackend.repository.UserRepository;
@Service
public class R4JFetchImpl implements R4JFetch {

    @Autowired
    JsonConverter jsonConverter;
    @Autowired
    private UserRepository userRepository;
    final R4J r4J = new R4J(APICredential.CRED);
    public void test(){
        ActiveGameData gameData = LiveClientDataAPI.getGameData();

        List<GameEvent> events = gameData.getEvents();
        for(GameEvent ev : events){
            ev.getEventName();
        }
    }


    public List<MatchHistoryDTO> getMatchHistory(User user){
        Summoner summoner = SummonerAPI.getInstance().getSummonerByName(user.getLeagueShard(), user.getLOLUsername());
        System.out.println(summoner.getSummonerId());
        MatchListBuilder builder = new MatchListBuilder();
        builder = builder.withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform());
        List<String> matchHistory = builder.withCount(21).get();
        System.out.println(matchHistory);
        MatchBuilder matchBuilder = new MatchBuilder(summoner.getPlatform());
        List<MatchHistoryDTO> data = new ArrayList<>();
        for (String s : matchHistory) {
            LOLMatch match = matchBuilder.withId(s).getMatch();
            MatchHistoryDTO matchHistoryDTO = new MatchHistoryDTO();


                System.out.println(match.getGameEndAsDate());
                List<MatchParticipant> matchParticipants = match.getParticipants();
                for (int j = 0; j < match.getParticipants().size(); j++) {
                    String Puuid = matchParticipants.get(j).getPuuid();
                    if (Puuid.equals(summoner.getPUUID())) {
                        MatchParticipant matchParticipant = matchParticipants.get(j);
                        matchHistoryDTO.setChampionName(matchParticipant.getChampionName());
                        System.out.println(matchParticipant.getKills());
                        matchHistoryDTO.setKills(matchParticipant.getKills());
                        matchHistoryDTO.setAssists(matchParticipant.getAssists());
                        matchHistoryDTO.setDeaths(matchParticipant.getDeaths());
                        matchHistoryDTO.setDidWin(matchParticipant.didWin());
                        matchHistoryDTO.setSummonerName(matchParticipant.getSummonerName());
                        data.add(matchHistoryDTO);


                        break;

                    }
                }

        }

      return data;

    }

    public  Map<String, Integer>  fetchMostPlayedChampions(User user){
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

        Map<String, Long> map = champions.stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
        List<Map.Entry<String, Long>> mostPlayed = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(mostPlayed);
        Map<String, Integer> data = new HashMap<>();
        for (Map.Entry<String, Long> stringLongEntry : mostPlayed) {
            float wins = stringLongEntry.getValue();
            float games = 21;


            data.put(stringLongEntry.getKey(), Math.round((wins / games) * 100));
            System.out.println(stringLongEntry.getValue());
        }
        return data;
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
        return Math.round(calculateWinRate(wins, loses)*100);
    }

    public float calculateWinRate(float wins, float loses) {
        float allGames = wins + loses;
        return wins / allGames;
    }

    public List<ChampionMatchHistoryData> getDataFromUserMatch(int championId, String summonerName, LeagueShard server) throws Exception {
            List<ChampionMatchHistoryData> championMatchHistoryData = new ArrayList<>();
            Summoner summoner = SummonerAPI.getInstance().getSummonerByName(server, summonerName);
            MatchListBuilder builder = new MatchListBuilder();
            builder = builder.withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform());
            MatchBuilder matchBuilder = new MatchBuilder(summoner.getPlatform());
            ArrayList<Integer> queues = queueIds();

            for (int i = 0; i < 3; i++) {
                Optional<GameQueueType> gameQueueType = GameQueueType.getFromId(queues.get(i));
                if (gameQueueType.isPresent()) {
                    List<String> solo = builder.withQueue(gameQueueType.get()).withCount(100).get();
                    System.out.println(gameQueueType.get());
                    for (String s : solo) {
                        LOLMatch match = matchBuilder.withId(s).getMatch();
                        if (match.getGameStartTimestamp() > 1641513601000L) {
                            List<MatchParticipant> matchParticipants = match.getParticipants();
                            for (int j = 0; j < match.getParticipants().size(); j++) {
                                String PUUID = matchParticipants.get(j).getPuuid();
                                if (PUUID.equals(summoner.getPUUID())) {
                                    MatchParticipant matchParticipant = matchParticipants.get(j);
                                    MatchTeam matchTeam;
                                    double gameTime = calculateGameTime(match.getGameDuration());
                                    if (match.getTeams().get(0).getTeamId() == matchParticipant.getTeam()) {
                                        matchTeam = match.getTeams().get(0);
                                    } else {
                                        matchTeam = match.getTeams().get(1);
                                    }
                                    if (matchParticipants.get(j).getChampionId() == championId) {
                                        ChampionMatchHistoryData data = new ChampionMatchHistoryData();
                                        setData(matchParticipant, matchTeam, gameTime, data);
                                        System.out.println(data.toString());
                                        championMatchHistoryData.add(data);
                                    }
                                    break;
                                }
                            }

                        }
                    }
                }
            }
            jsonConverter.convertChampionMatchHistoryDataToJSON(championMatchHistoryData);
            return championMatchHistoryData;
    }


    private void setData(MatchParticipant matchParticipant, MatchTeam matchTeam, double gameTime, ChampionMatchHistoryData data) {
        data.setCreepScorePM(calculateScorePM(matchParticipant.getTotalMinionsKilled(), gameTime));
        data.setCrowdControlScore(calculateScorePM(matchParticipant.getTimeCCingOthers(), gameTime));
        data.setVisionScorePM(calculateScorePM(matchParticipant.getVisionScore(), gameTime));
        data.setDamagePM(calculateScorePM(matchParticipant.getTotalDamageDealtToChampions(), gameTime));
        data.setSelfMitigatedPM(calculateScorePM(matchParticipant.getDamageSelfMitigated(), gameTime));
        data.setKDA(calculateKDA(matchParticipant.getKills(), matchParticipant.getDeaths(), matchParticipant.getAssists()));
        data.setKP(calculateKP(matchParticipant.getKills(), matchParticipant.getAssists(), matchTeam));
        data.setObjectivesTaken(getObjectives(matchTeam));
    }


    public double calculateGameTime(double gameTimeInSeconds){
        return (Math.round((gameTimeInSeconds / 60) * 100.0) / 100.0);
    }

    public double calculateScorePM(int Score, double gameTime){
        return (Math.round((Score / gameTime) * 10.0) / 10.0);

    }

    public ArrayList<Integer> queueIds(){
        ArrayList<Integer> queues = new ArrayList<>();
        queues.add(400);
        queues.add(420);
        queues.add(440);
        return queues;
    }

    public double calculateKDA(double kills, double deaths, double assists){
        return (Math.round(((kills + assists) / deaths) * 10.00) / 10.00);
    }

    public double calculateKP(double kills, double assists, MatchTeam matchTeam){
        return (Math.round(((kills + assists) / matchTeam.getObjectives().get("champion").getKills()) * 100.0 )/ 100.0);
    }

    private ObjectiveMatchHistoryData getObjectives(MatchTeam matchTeam){
        Map<String, ObjectiveStats> stats = matchTeam.getObjectives();
        ObjectiveMatchHistoryData data = new ObjectiveMatchHistoryData();
        data.setDragonKills(stats.get("dragon").getKills());
        data.setDragonKills(stats.get("inhibitor").getKills());
        data.setDragonKills(stats.get("tower").getKills());
        data.setDragonKills(stats.get("riftHerald").getKills());
       return data;
    }


    public Map<String, Integer>getMostPlayedChampions(String username){
        Map<String, Integer> data = new HashMap<>();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return fetchMostPlayedChampions(user);
        }
        return data;
    }
}
