package com.todoback.todobackend.service.LOL;

import com.todoback.todobackend.domain.ChampionMatchHistoryData;

import java.io.IOException;
import java.util.List;

public interface JsonConverter {
    void convertChampionMatchHistoryDataToJSON(List<List<ChampionMatchHistoryData>> championMatchHistoryData) throws Exception;
}
