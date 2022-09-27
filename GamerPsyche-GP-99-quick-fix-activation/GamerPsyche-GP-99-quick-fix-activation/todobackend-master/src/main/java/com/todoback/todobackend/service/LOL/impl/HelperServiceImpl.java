package com.todoback.todobackend.service.LOL.impl;

import com.merakianalytics.orianna.types.common.Queue;
import com.todoback.todobackend.domain.ChampionMatchHistoryData;
import com.todoback.todobackend.domain.ObjectiveMatchHistoryData;
import com.todoback.todobackend.service.LOL.HelperService;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchTeam;
import no.stelar7.api.r4j.pojo.lol.match.v5.ObjectiveStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.todoback.todobackend.service.LOL.OriannaFetch;
import com.todoback.todobackend.repository.UserRepository;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import com.todoback.todobackend.service.LOL.R4JFetch;
import com.todoback.todobackend.domain.User;
@Service
public class HelperServiceImpl implements HelperService {

    @Autowired
    private R4JFetch r4jFetch;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OriannaFetch oriannaFetch;


    public float fetchWinRateByQueue(String username, int queue) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Queue queueOrianna = Queue.withId(queue);
            if(queue == 400){
                Optional<GameQueueType> queueOptional = GameQueueType.getFromId(queue);
                if (queueOptional.isPresent()) {
                    GameQueueType queueR4J= queueOptional.get();
                    float data = r4jFetch.R4JFetchWinRateByQueue(user, queueR4J);
                    System.out.println("r4j");
                    return data;
                }
            }
            System.out.println("Ranked");
            try {
                return oriannaFetch.getWinRateByQueue(queueOrianna, user);
            } catch (Exception e) {
                if (e.getMessage().equals("data null")) {
                    Optional<GameQueueType> queueOptional = GameQueueType.getFromId(queue);
                    if (queueOptional.isPresent()) {
                        GameQueueType queueR4J= queueOptional.get();
                        float data = r4jFetch.R4JFetchWinRateByQueue(user, queueR4J);
                        System.out.println("r4j");
                        return data;
                    }
                }
            }
        }
        return -1;
    }

    public ArrayList<Integer> queueIds(){
        ArrayList<Integer> queues = new ArrayList<>();
        queues.add(400);
        queues.add(420);
        queues.add(440);
        return queues;
    }

    public GameQueueType gameQueueTypePresent(int i, ArrayList<Integer> queues){
        Optional<GameQueueType> gameQueueType = GameQueueType.getFromId(queues.get(i));
        return gameQueueType.orElse(null);
    }

    public double calculateGameTime(double gameTimeInSeconds){
        return (Math.round((gameTimeInSeconds / 60) * 100.0) / 100.0);
    }

    public void setData(MatchParticipant matchParticipant, MatchTeam matchTeam, double gameTime, ChampionMatchHistoryData data, boolean searchedUser) {
        data.setUsername(matchParticipant.getSummonerName());
        data.setDidWon(matchParticipant.didWin());
        data.setSearchedUser(searchedUser);
        data.setChampion(matchParticipant.getChampionName());
        data.setCreepScorePM(calculateScorePM(matchParticipant.getTotalMinionsKilled(), gameTime));
        data.setCrowdControlScore(calculateScorePM(matchParticipant.getTimeCCingOthers(), gameTime));
        data.setVisionScorePM(calculateScorePM(matchParticipant.getVisionScore(), gameTime));
        data.setDamagePM(calculateScorePM(matchParticipant.getTotalDamageDealtToChampions(), gameTime));
        data.setSelfMitigatedPM(calculateScorePM(matchParticipant.getDamageSelfMitigated(), gameTime));
        data.setKDA(calculateKDA(matchParticipant.getKills(), matchParticipant.getDeaths(), matchParticipant.getAssists()));
        data.setKP(calculateKP(matchParticipant.getKills(), matchParticipant.getAssists(), matchTeam));
        data.setObjectivesTaken(getObjectives(matchTeam));
    }

    public double calculateScorePM(int Score, double gameTime){
        return (Math.round((Score / gameTime) * 10.0) / 10.0);

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

    public MatchTeam getUserTeam(LOLMatch match, MatchParticipant matchParticipant) {
        MatchTeam matchTeam;
        if (match.getTeams().get(0).getTeamId() == matchParticipant.getTeam()) {
            matchTeam = match.getTeams().get(0);
        } else {
            matchTeam = match.getTeams().get(1);
        }
        return matchTeam;
    }

}
