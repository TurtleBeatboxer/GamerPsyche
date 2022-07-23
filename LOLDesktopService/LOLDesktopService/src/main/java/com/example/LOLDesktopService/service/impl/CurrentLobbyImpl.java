package com.example.LOLDesktopService.service.impl;

import com.example.LOLDesktopService.domain.Action;
import com.example.LOLDesktopService.service.CurrentLobby;
import com.google.gson.Gson;
import com.stirante.lolclient.ClientApi;
import com.stirante.lolclient.ClientConnectionListener;
import com.stirante.lolclient.ClientWebSocket;
import generated.*;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class CurrentLobbyImpl implements CurrentLobby {
    final Gson gson = new Gson();
    final ClientApi api = new ClientApi();

    private static ClientWebSocket socket;
        public void findSummonersInLobby() throws IOException {
            api.addClientConnectionListener(new ClientConnectionListener() {
                @Override
                public void onClientConnected() {
                    try {
                        if (!api.isAuthorized()) {
                            System.out.println("Not");
                            return;
                        }
                        LolSummonerSummoner summoner = api.executeGet("/lol-summoner/v1/current-summoner", LolSummonerSummoner.class).getResponseObject();
                        System.out.println(summoner.summonerId);
                        System.out.println(summoner.accountId);
                        socket = api.openWebSocket();
                        socket.setSocketListener(new ClientWebSocket.SocketListener() {
                            @Override
                            public void onEvent(ClientWebSocket.Event event) {
                                if (event.getUri().equalsIgnoreCase("/lol-champ-select/v1/session")) {
                                    int myCellID = -1;
                                    LolChampSelectChampSelectSession session =
                                            (LolChampSelectChampSelectSession) event.getData();


                                    List<LolChampSelectChampSelectPlayerSelection> enemyTeam = session.theirTeam;
                                    List<LolChampSelectChampSelectPlayerSelection> myTeam = session.myTeam;
                                    for (LolChampSelectChampSelectPlayerSelection player : myTeam) {
                                        myCellID = Math.toIntExact(player.cellId);
                                    }
                                    for (Object actions : session.actions) {
                                        for (Object action : ((List) actions)) {
                                            Map<String, Object> a = (Map<String, Object>) action;
                                            String cellID = a.get("actorCellId").toString();
                                            float b = Float.parseFloat(cellID);
                                            int c = Math.round(b);
                                            for (LolChampSelectChampSelectPlayerSelection player : myTeam) {
                                                if (Math.toIntExact(player.cellId) == c) {
                                                    System.out.println("Send action");
                                                /*try{
                                                    System.out.println(api.executeGet("/lol-summoner/v1/current-summoner", LolSummonerSummoner.class).getResponseObject().displayName);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }*/
                                                    try {
                                                    /*LolGeoinfoWhereAmIRequest req = api.executeGet("/lol-geoinfo/v1/whereami", LolGeoinfoWhereAmIRequest.class).getResponseObject();
                                                    LolGeoinfoGeoInfoResponse req2 = api.executeGet("/lol-geoinfo/v1/whereami", LolGeoinfoGeoInfoResponse.class).getResponseObject();
                                                    System.out.println(req.ipAddress);
                                                    System.out.println(req2.geoInfo.country);

                                                    System.out.println(req2.geoInfo.city);
                                                    System.out.println(req2.geoInfo.region);*/
                                                        LolChampSelectChampSelectTimer timer = api.executeGet("/lol-champ-select/v1/session/timer", LolChampSelectChampSelectTimer.class).getResponseObject();
                                                        System.out.println(timer.phase);
                                                        if (timer.phase.equalsIgnoreCase("GAME_STARTING")) {
                                                            socket.close();
                                                            api.stop();
                                                            System.out.println("STOP");
                                                            return;
                                                        }
                                                        String summonerName = api.executeGet("/lol-summoner/v1/summoners/" + player.summonerId, LolSummonerSummoner.class).getResponseObject().displayName;
                                                        Action data = setActionClass(getInt(a.get("actorCellId").toString()), getInt(a.get("championId").toString()), Boolean.parseBoolean(a.get("completed").toString()), getInt(a.get("id").toString()), Boolean.parseBoolean(a.get("isAllyAction").toString()), Boolean.parseBoolean(a.get("isInProgress").toString()), getInt(a.get("pickTurn").toString()), a.get("type").toString(), summonerName, player.assignedPosition);
                                                        System.out.println(data);
                                                        sendHTTPReq(data);
                                                    } catch (IOException | URISyntaxException | InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }


                            }

                            @Override
                            public void onClose(int code, String reason) {

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onClientDisconnected() {
                   socket.close();
                }
            });
          /*
            socket.close();
            api.stop();*/

        }
    public Action setActionClass(int actorCellId, int championId, boolean completed, int id, boolean isAllyAction, boolean isInProgress, int pickTurn, String type, String summonerName, String position){
        Action data = new Action();
        data.setAllyAction(isAllyAction);
        data.setActorCellId(actorCellId);
        data.setChampionId(championId);
        data.setCompleted(completed);
        data.setId(id);
        data.setInProgress(isInProgress);
        data.setPickTurn(pickTurn);
        data.setType(type);
        data.setPosition(position);
        data.setSummonerName(summonerName);
        return data;
    }


    public int getInt(String i){
        float b = Float.parseFloat(i);
        return Math.round(b);
    }


    public void sendHTTPReq(Action data) throws URISyntaxException, IOException, InterruptedException {
            System.out.println("Sending");

            String d = gson.toJson(data);

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/app")).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(d)).build();

           HttpResponse<String> response = HttpClient.newBuilder().build().send(req, HttpResponse.BodyHandlers.ofString());
           System.out.println(response);


    }


}