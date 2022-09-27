package com.todoback.todobackend.service.LOL.impl;

import com.google.gson.Gson;
import com.todoback.todobackend.domain.ChampionMatchHistoryData;
import com.todoback.todobackend.service.LOL.JsonConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class JsonConverterImpl implements JsonConverter {

    public static final String SEPARATOR = System.getProperty("file.separator");

    @Value("${jsongenerator.file.path}")
    private String jsonFilePath;

    public void convertChampionMatchHistoryDataToJSON(List<List<ChampionMatchHistoryData>> championMatchHistoryData) throws Exception {
                Gson gson = new Gson();
                try (FileWriter writer = new FileWriter(jsonFilePath + SEPARATOR + "MatchHistoryData.json")) {
                    gson.toJson(championMatchHistoryData, writer);
                    System.out.println("JSON converted");
                } catch (Exception e){
                    System.out.println("Chuj zjebało się");
                }
            }
}
