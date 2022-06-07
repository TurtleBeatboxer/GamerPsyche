package com.todoback.todobackend.service.impl;

import com.merakianalytics.orianna.Orianna;
import com.todoback.todobackend.domain.User;
import com.todoback.todobackend.service.OriannaFetch;
import com.todoback.todobackend.service.OriannaUsagePreparationService;
import org.springframework.stereotype.Service;

@Service
public class OriannaFetchImpl implements OriannaFetch {
    OriannaUsagePreparationService oriannaUsagePreparationService = new OriannaUsagePreparationService();
    @Override
    public void fetchBasicInfo() { }


    /*@Override

    }*/
 }
