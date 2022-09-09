package com.todoback.todobackend.service.LOL.impl;

import com.google.gson.Gson;
import com.todoback.todobackend.configuration.APICredential;
import com.todoback.todobackend.domain.*;
import com.todoback.todobackend.repository.UserRepository;
import com.todoback.todobackend.service.LOL.JsonConverter;
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
import no.stelar7.api.r4j.pojo.lol.match.v5.ObjectiveStats;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class R4JFetchImpl implements R4JFetch {
    final Gson gson = new Gson();

    @Autowired
    JsonConverter jsonConverter;
    @Autowired
    private UserRepository userRepository;
    final R4J r4J = new R4J(APICredential.CRED);

    public List<MatchHistoryDTO> getMatchHistory(String username){
        List<MatchHistoryDTO> data = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return fetchMatchHistory(user);
        }
        return data;
    }


    public List<MatchHistoryDTO> fetchMatchHistory(User user){
        Summoner summoner = SummonerAPI.getInstance().getSummonerByName(user.getLeagueShard(), user.getLOLUsername());
        List<String> matchHistory = new MatchListBuilder().withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform()).withCount(21).get();
        MatchBuilder matchBuilder = new MatchBuilder(summoner.getPlatform());
        List<MatchHistoryDTO> data = new ArrayList<>();
        for (String s : matchHistory) {
            LOLMatch match = matchBuilder.withId(s).getMatch();
            MatchHistoryDTO matchHistoryDTO = new MatchHistoryDTO();
                List<MatchParticipant> matchParticipants = match.getParticipants();
                for (int j = 0; j < match.getParticipants().size(); j++) {
                    String Puuid = matchParticipants.get(j).getPuuid();
                    if (Puuid.equals(summoner.getPUUID())) {
                        setMatchHistoryDTO(matchHistoryDTO, matchParticipants.get(j));
                        data.add(matchHistoryDTO);
                        break;
                    }
                }
        }
      return data;
    }

    private void setMatchHistoryDTO(MatchHistoryDTO matchHistoryDTO, MatchParticipant matchParticipant) {
        matchHistoryDTO.setChampionName(matchParticipant.getChampionName());
        matchHistoryDTO.setKills(matchParticipant.getKills());
        matchHistoryDTO.setAssists(matchParticipant.getAssists());
        matchHistoryDTO.setDeaths(matchParticipant.getDeaths());
        matchHistoryDTO.setDidWin(matchParticipant.didWin());
        matchHistoryDTO.setSummonerName(matchParticipant.getSummonerName());
    }

    public  Map<String, Integer>  fetchMostPlayedChampions(User user){
        Summoner summoner = SummonerAPI.getInstance().getSummonerByName(user.getLeagueShard(), user.getLOLUsername());
        List<String> matchHistory  = new MatchListBuilder().withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform()).withCount(21).get();
        // Znajduje championy który zagrał dany gracz w swoim match history i umieszcza w array champions
        List<String> champions = findChampions(summoner, matchHistory);
        List<Map.Entry<String, Long>> mostPlayed = getEntries(champions);
        return getPlayRatePerChampion(mostPlayed);
    }

    private  Map<String, Integer>  getPlayRatePerChampion(List<Map.Entry<String, Long>> mostPlayed) {
        Map<String, Integer> data = new HashMap<>();
        for (Map.Entry<String, Long> stringLongEntry : mostPlayed) {
            float wins = stringLongEntry.getValue();
            float games = 21;
            data.put(stringLongEntry.getKey(), Math.round((wins / games) * 100));
        }
        return data;
    }

    private List<Map.Entry<String, Long>> getEntries(List<String> champions) {
        Map<String, Long> map = champions.stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toList());
    }

    private List<String> findChampions(Summoner summoner, List<String> matchHistory) {
        List<String> champions = new ArrayList<>();
        MatchBuilder matchBuilder = new MatchBuilder(summoner.getPlatform());
        for(String m: matchHistory){
            LOLMatch match = matchBuilder.withId(m).getMatch();
            List<MatchParticipant> matchParticipants = match.getParticipants();
            for (int j = 0; j < match.getParticipants().size(); j++) {
                String Puuid = matchParticipants.get(j).getPuuid();
                if (Puuid.equals(summoner.getPUUID())) {
                    champions.add(matchParticipants.get(j).getChampionName());
                }
            }
        }
        return champions;
    }


    public float R4JFetchWinRateByQueue(User user, GameQueueType queueType) {
        Summoner summoner = SummonerAPI.getInstance().getSummonerByName(user.getLeagueShard(), user.getLOLUsername());
        MatchBuilder matchBuilder = new MatchBuilder(summoner.getPlatform());
        List<String> solo =  new MatchListBuilder().withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform()).withQueue(queueType).withCount(100).get();
        return getWinRate(summoner, matchBuilder, solo);
    }

    private int getWinRate(Summoner summoner, MatchBuilder matchBuilder, List<String> solo) {
        float wins = 0;
        float loses = 0;
        for (String s : solo) {
            LOLMatch match = matchBuilder.withId(s).getMatch();
            if (match.getGameStartTimestamp() > 1641513601000L) {
                List<MatchParticipant> matchParticipants = match.getParticipants();
                for (int j = 0; j < match.getParticipants().size(); j++) {
                    String Puuid = matchParticipants.get(j).getPuuid();
                    if (Puuid.equals(summoner.getPUUID())) {
                        if (matchParticipants.get(j).didWin()) {
                            wins++;
                        } else {
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

    public List<ChampionMatchHistoryData> getDataFromUserMatch(String body) throws Exception {
        Action object = gson.fromJson(body, Action.class);
        Optional<User> userOptional = userRepository.findByLolUsername(object.getSummonerName());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(object.getChampionId() > 0){
                return fetchDataFromUserMatch(object.getChampionId(), object.getSummonerName(), user.getLeagueShard());
            }
        }
        return null;
    }

    public List<ChampionMatchHistoryData> fetchDataFromUserMatch(int championId, String summonerName, LeagueShard server) throws Exception {
            List<ChampionMatchHistoryData> championMatchHistoryData = new ArrayList<>();
            Summoner summoner = SummonerAPI.getInstance().getSummonerByName(server, summonerName);
            MatchListBuilder builder = new MatchListBuilder().withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform());
            MatchBuilder matchBuilder = new MatchBuilder(summoner.getPlatform());
            ArrayList<Integer> queues = queueIds();
            for (int i = 0; i < 3; i++) {
                    List<String> solo = builder.withQueue(gameQueueTypePresent(i, queues)).withCount(100).get();
                    for (String s : solo) {
                        LOLMatch match = matchBuilder.withId(s).getMatch();
                        if (match.getGameStartTimestamp() > 1641513601000L) {
                            getMatchHistoryData(championId, championMatchHistoryData, summoner, match);
                        }
                    }
            }
            jsonConverter.convertChampionMatchHistoryDataToJSON(championMatchHistoryData);
            return championMatchHistoryData;
    }

    private void getMatchHistoryData(int championId, List<ChampionMatchHistoryData> championMatchHistoryData, Summoner summoner, LOLMatch match) {
        for (int j = 0; j < match.getParticipants().size(); j++) {
            String PUUID = match.getParticipants().get(j).getPuuid();
            if (PUUID.equals(summoner.getPUUID())) {
                MatchParticipant matchParticipant = match.getParticipants().get(j);
                double gameTime = calculateGameTime(match.getGameDuration());
                MatchTeam matchTeam = getUserTeam(match, matchParticipant);
                if (match.getParticipants().get(j).getChampionId() == championId) {
                    ChampionMatchHistoryData data = new ChampionMatchHistoryData();
                    setData(matchParticipant, matchTeam, gameTime, data);
                    championMatchHistoryData.add(data);
                }
                break;
            }
        }
    }

    private MatchTeam getUserTeam(LOLMatch match, MatchParticipant matchParticipant) {
        MatchTeam matchTeam;
        if (match.getTeams().get(0).getTeamId() == matchParticipant.getTeam()) {
            matchTeam = match.getTeams().get(0);
        } else {
            matchTeam = match.getTeams().get(1);
        }
        return matchTeam;
    }

    private GameQueueType gameQueueTypePresent(int i, ArrayList<Integer> queues){
        Optional<GameQueueType> gameQueueType = GameQueueType.getFromId(queues.get(i));
        if (gameQueueType.isPresent()){
            return gameQueueType.get();
        }
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
