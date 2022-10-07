package com.todoback.todobackend.service.LOL.impl;

import com.google.gson.Gson;
import com.todoback.todobackend.configuration.APICredential;
import com.todoback.todobackend.domain.*;
import com.todoback.todobackend.repository.UserRepository;
import com.todoback.todobackend.service.LOL.HelperService;
import com.todoback.todobackend.service.LOL.JsonConverter;
import com.todoback.todobackend.service.LOL.R4JFetch;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.basic.constants.types.lol.RoleType;
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

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class R4JFetchImpl implements R4JFetch {
    final Gson gson = new Gson();

    @Autowired
    JsonConverter jsonConverter;
    @Autowired
    private HelperService helperService;
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
            ArrayList<Integer> queues = helperService.queueIds(true, true, true);
            for (int i = 0; i < 3; i++) {
                    List<String> solo = builder.withQueue(helperService.gameQueueTypePresent(queues.get(i))).withCount(100).get();
                    for (String s : solo) {
                        LOLMatch match = matchBuilder.withId(s).getMatch();
                        if (match.getGameStartTimestamp() > 1641513601000L) {
                            getMatchHistoryData(championId, championMatchHistoryData, summoner, match);
                        }
                    }
            }
            //jsonConverter.convertChampionMatchHistoryDataToJSON(championMatchHistoryData);
            return championMatchHistoryData;
    }

    private void getMatchHistoryData(int championId, List<ChampionMatchHistoryData> championMatchHistoryData, Summoner summoner, LOLMatch match) {
        for (int j = 0; j < match.getParticipants().size(); j++) {
            String PUUID = match.getParticipants().get(j).getPuuid();
            if (PUUID.equals(summoner.getPUUID())) {
                MatchParticipant matchParticipant = match.getParticipants().get(j);
                double gameTime = helperService.calculateGameTime(match.getGameDuration());
                MatchTeam matchTeam = helperService.getUserTeam(match, matchParticipant);
                if (match.getParticipants().get(j).getChampionId() == championId) {
                    ChampionMatchHistoryData data = new ChampionMatchHistoryData();
                    helperService.setData(matchParticipant, matchTeam, gameTime, data, true);
                    championMatchHistoryData.add(data);
                }
                break;
            }
        }
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


    //mozna zmienic na Optional<Float>
    //w razie pustej listy wyrzuca -1 co mozna gdzies podpiac jako "brak danych"
    public float calculateWR(List<LOLMatch> matches, String puuid) {
        int wins = 0;
        int games = 0;
        if (matches.isEmpty())
            return -1;
        for (LOLMatch match : matches) {
            List<MatchParticipant> matchParticipants = match.getParticipants();
            for (int j = 0; j < match.getParticipants().size(); j++) {
                if (matchParticipants.get(j).getPuuid().equals(puuid)) {
                    games++;
                    if (matchParticipants.get(j).didWin())
                        wins++;
                }
            }
        }
        return calculateWinRate(wins, games);
    }

    //w razie pustej listy wyrzuca -1 co mozna gdzies podpiac jako "brak danych"
    public float calculateWR(List<MatchParticipant> matches) {
        int wins = 0;
        int games = 0;
        if (matches.isEmpty())
            return -1;
        for (MatchParticipant matchParticipant : matches) {
            if (matchParticipant.didWin())
                wins++;
            games++;
        }
        return calculateWinRate(wins, games);
    }

    public List<LOLMatch> getSoloMatches(User user) {
        Summoner summoner = SummonerAPI.getInstance().getSummonerByName(user.getLeagueShard(), user.getLOLUsername());
        MatchListBuilder builder = new MatchListBuilder();
        builder = builder.withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform());
        MatchBuilder matchBuilder = new MatchBuilder(summoner.getPlatform());

        List<String> soloIDs = builder.withQueue(GameQueueType.TEAM_BUILDER_RANKED_SOLO).withCount(100).get();
        ArrayList<LOLMatch> solo = new ArrayList<LOLMatch>();
        for (String s : soloIDs) {
            if (!s.isBlank() || !s.equals("0"))
                solo.add(matchBuilder.withId(s).getMatch());
        }
        return solo;
    }

    public ArrayList<LOLMatch> getFlexMatches(User user) {
        Summoner summoner = SummonerAPI.getInstance().getSummonerByName(user.getLeagueShard(), user.getLOLUsername());
        MatchListBuilder builder = new MatchListBuilder();
        builder = builder.withPuuid(summoner.getPUUID()).withPlatform(summoner.getPlatform());
        MatchBuilder matchBuilder = new MatchBuilder(summoner.getPlatform());



        List<String> flexIDs = builder.withQueue(GameQueueType.RANKED_FLEX_SR).withCount(100).get();
        ArrayList<LOLMatch> flex = new ArrayList<LOLMatch>();
        for (String f : flexIDs)
            flex.add(matchBuilder.withId(f).getMatch());
        Collections.reverse(flex);
        return flex;
    }

    //mozna to kiedys zmienic zeby nie wrzucac wszedzie listy meczy i potem nie szukac summonera
    //w kazdym mzeczu, bedzie ekonomieczniej, ale mi sie nie chce i nie robnie tego
    //teraz
    //bo w tym etapioe wszedzei to sie oplaca brac listy of participants (uporzadkowana)
    //
    //to jest pod kazdym indeksem TEN SAM SUMMONER!
    public List<MatchParticipant> matchesToParticipants(List<LOLMatch> inputList, String puuid) {
        List<MatchParticipant> outputList = new ArrayList<>();
        for (LOLMatch lolMatch : inputList) {
            outputList.add(findMatchParticipant(lolMatch.getParticipants(), puuid));
        }
        return outputList;
    }

    //moze rzucic pustego jakby cos skrajnie dziwnego sie stalo(?)
    public MatchParticipant findMatchParticipant(List<MatchParticipant> matchParticipants, String puuid) {
        for (MatchParticipant matchParticipant : matchParticipants) {
            if (matchParticipant.getPuuid().equals(puuid))
                return matchParticipant;
        }
        return new MatchParticipant();
    }

    //liczy cold albo winstreak wr
    //trzeba wywolac dla tego i dla tego
    //bierze wszystkie tego rodzaju streaki z tabeli streakow tworzonej wywolaniem findStreaks
    public float calculateStreakWR(List<StreakDTO> inputList, StreakType streakType) {
        List<LOLMatch> matchList = new ArrayList<>();
        for (StreakDTO streakDTO : inputList) {
            if (streakDTO.getStreakType() == streakType)
                matchList.addAll(streakDTO.getPostStreak());
        }

        if (inputList.isEmpty())
            return -1;

        return calculateWR(matchList, inputList.get(inputList.size() - 1).getPuuid());
    }

    //moze byc empty
    public List<StreakDTO> findStreaks(List<LOLMatch> matches, String puuid) {

        List<StreakDTO> allStreaksWithTypes = new ArrayList<>();
        List<MatchParticipant> matchParticipants = matchesToParticipants(matches, puuid);
        int streak = 0;
        int pom = 0;
        boolean pastGame = false;

        for (MatchParticipant matchParticipant : matchParticipants) {

            //dla pierwszej iteracji
            if (streak == 0)
                streak++;

                //dla nastepnych
            else if (pastGame == matchParticipant.didWin())
                streak++;

                //zerowanie w przypadku braku streaka
            else
                streak = 0;

            //w przypadku znalezienia streaka
            //dodajemy mecze po streaku do tabeli streakow
            //oddzielnie kazdy streak i typ streaka (cold, win)
            //sprawdzajac czy ostatnia gra to win czy nie, sprawdzamy rozdaj streaka
            if (streak == 3) {
                allStreaksWithTypes.add(new StreakDTO(
                        findGamesInARow(matches, pom),
                        findStreakType(matchParticipant),
                        puuid));

                //resetujac parametr szukamy nastepnego streaka i przechodzimy dalej przez liste
                streak = 0;
            }

            //przypisujemy pod koniec iteracji wartosc pomocniczej pastGame
            pastGame = matchParticipant.didWin();
            pom++;
        }

        return allStreaksWithTypes;
    }

    public StreakType findStreakType(MatchParticipant matchParticipant) {
        int i = matchParticipant.didWin() ? 1 : 0;
        switch (i) {
            case 1:
                return StreakType.WINSTREAK;
            case 0:
                return StreakType.COLDSTREAK;
            default:
                return StreakType.NONE;
        }

    }

    public boolean isTiltGame (LOLMatch match, String puuid){
        //zmienne takie ktore mozna byloby chceic zmieniac tutaj wylistowalem dla wygody
        float tiltKDA = 0.35f;

        MatchParticipant matchParticipant = findMatchParticipant(match.getParticipants(), puuid);

        return calculateKDA(matchParticipant) < tiltKDA;
    }
    //-1 gdy nie ma tilt gry
    public float calculateTiltGameWR(List<LOLMatch> matches, String puuid) {

        ArrayList<LOLMatch> afterTilt = new ArrayList<>();

        for (LOLMatch match : matches) {

            if (isTiltGame(match, puuid)) {

                afterTilt.addAll(findGamesInARow(matches, matches.indexOf(match)));

            }
        }
        if (afterTilt.isEmpty())
            return -1;

        return calculateWR(afterTilt, puuid);
    }

    public float calculateKDA(MatchParticipant matchParticipant) {
        return (float) (matchParticipant.getKills()
                +
                matchParticipant.getAssists())
                /
                matchParticipant.getDeaths();
    }

    //BIERZE LISTE, INDEX MECZU
    //ZWRACA LISTE ZAWIERAJACA MECZE ZAGRANE IN A ROW PO MECZU INDEKSOWANYM
    //bez indeksowanego oczywiscie na potrzeby metody tak sie dzieje
    public List<LOLMatch> findGamesInARow(List<LOLMatch> inputList, int matchIndex) {
        //zmienna - czas miedzy meczami - w minutach
        long deltaTime = 75L;

        List<LOLMatch> outputList = new ArrayList<LOLMatch>();
        ZonedDateTime matchEndTime = inputList.get(matchIndex).getGameEndAsDate();

        //zmienne pomocnicze w pentli
        boolean isInRow = true;
        while (isInRow) {
            matchIndex += 1;
            if(matchIndex == inputList.size())
                break;

            //to sprawdza czy zdanie odwrotne do "czas rozpoczecia nastepnej gry minus godzina jest wczesniej niz czas zakonczenia poprzedniej" jest prawdziwe
            if (!inputList.get(matchIndex)
                    .getGameStartAsDate()
                    .minusMinutes(deltaTime)
                    .isBefore(matchEndTime)) {
                isInRow = false;
            }else {
                //dodaje mecz do listy meczy w jednej sesji, wybiera nowy "znacznik" na iteracje i zmienia indeks
                outputList.add(inputList.get(matchIndex));
                matchEndTime = inputList.get(matchIndex).getGameEndAsDate();
            }
        }

        return outputList;
    }

    public float calculateRoleWR(List<MatchParticipant> inputList, RoleType currentRole) {
        List<MatchParticipant> matchParticipants = new ArrayList<>();
        for (MatchParticipant matchParticipant : inputList) {
            if (currentRole.equals(matchParticipant.getRole()))
                matchParticipants.add(matchParticipant);
        }
        return calculateWR(matchParticipants);
    }
}
