package com.todoback.todobackend.service.impl;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.todoback.todobackend.service.OriannaFetch;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class OriannaFetchImpl implements OriannaFetch {

    @Override
    public void fetchBasicInfo() {
        System.out.println(Orianna.summonerNamed("TurtleBB").withRegion(Region.EUROPE_NORTH_EAST).get().getAccountId());
    }
 }
