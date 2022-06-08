package com.todoback.todobackend.service.orianna.impl;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.core.match.MatchHistory;
import com.merakianalytics.orianna.types.core.staticdata.Champion;
import com.todoback.todobackend.domain.User;
import com.todoback.todobackend.service.orianna.OriannaFetch;
import com.todoback.todobackend.service.orianna.OriannaUsagePreparationService;
import org.springframework.stereotype.Service;

@Service
public class OriannaFetchImpl implements OriannaFetch {
    @Override
    public void fetchBasicInfo(User user) {
        /*MatchHistory matchHistory = Orianna.summonerNamed(user.getLOLUsername()).withRegion(user.getLolRegion()).get().getCurrentMatch().getS
        matchHistory.get(1).getParticipants().get(2).getChampion().getSpells().get(0);*/
    }



 }
