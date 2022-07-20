package com.example.LOLDesktopService.service.impl;

import com.example.LOLDesktopService.service.CurrentLobby;
import com.stirante.lolclient.ClientApi;
import com.stirante.lolclient.ClientConnectionListener;
import generated.LolChampionsCollectionsChampion;
import generated.LolChampionsCollectionsChampionSkin;
import generated.LolSummonerSummoner;
import no.stelar7.api.r4j.impl.lol.lcu.LCUApi;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CurrentLobbyImpl implements CurrentLobby {
    public void findSummonersInLobby(){
        ClientApi api = new ClientApi();
        api.addClientConnectionListener(new ClientConnectionListener() {
            @Override
            public void onClientConnected() {
                try {
                    //Check if user is logged in
                    if (!api.isAuthorized()) {
                        System.out.println("Not logged in!");
                        return;
                    }
                    //Get current summoner
                    LolSummonerSummoner summoner = api.executeGet("/lol-summoner/v1/current-summoner", LolSummonerSummoner.class).getResponseObject();
                    System.out.println(summoner.displayName);
                    api.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClientDisconnected() {

            }
        });
    }
}
