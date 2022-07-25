package com.example.LOLDesktopService.service.impl;

import com.example.LOLDesktopService.domain.Action;
import com.example.LOLDesktopService.service.CurrentLobby;
import com.google.gson.Gson;
import com.stirante.lolclient.ClientApi;
import com.stirante.lolclient.ClientConnectionListener;
import com.stirante.lolclient.ClientWebSocket;
import generated.*;

import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.List;
import java.util.Map;

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
                            System.out.println("User is not authorized");
                            return;
                        }
                        LolSummonerSummoner summoner = api.executeGet("/lol-summoner/v1/current-summoner", LolSummonerSummoner.class).getResponseObject();
                        System.out.println("Connected to summoner with name " + summoner.displayName);

                        socket = api.openWebSocket();
                        socket.setSocketListener(new ClientWebSocket.SocketListener() {
                            @Override
                            public void onEvent(ClientWebSocket.Event event) {
                                if (event.getUri().equalsIgnoreCase("/lol-champ-select/v1/session")) {
                                    int myCellID = -1;
                                    LolChampSelectChampSelectSession session =
                                            (LolChampSelectChampSelectSession) event.getData();
                                    for (LolChampSelectChampSelectPlayerSelection player :  session.myTeam) {
                                        myCellID = Math.toIntExact(player.cellId);
                                    }
                                    for (Object actions : session.actions) {
                                        for (Object action : ((List) actions)) {
                                            Map<String, Object> a = (Map<String, Object>) action;

                                            for (LolChampSelectChampSelectPlayerSelection player :  session.myTeam) {
                                                if (Math.toIntExact(player.cellId) == getInt(a.get("actorCellId").toString())) {
                                                    System.out.println("Send action");
                                                    try {
                                                        LolChampSelectChampSelectTimer timer = api.executeGet("/lol-champ-select/v1/session/timer", LolChampSelectChampSelectTimer.class).getResponseObject();
                                                        System.out.println(timer.phase);
                                                        if (timer.phase.equalsIgnoreCase("GAME_STARTING")) {
                                                            socket.close();
                                                            api.stop();
                                                            System.out.println("Game is starting stopping api");
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
            String reqData = gson.toJson(data);
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/get/data/champion-select/by/action")).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(reqData)).build();
           HttpResponse<String> response = HttpClient.newBuilder().build().send(req, HttpResponse.BodyHandlers.ofString());
           System.out.println(response);
    }


}