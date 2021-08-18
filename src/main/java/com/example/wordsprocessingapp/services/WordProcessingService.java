package com.example.wordsprocessingapp.services;

import com.example.wordsprocessingapp.entities.Request;
import com.example.wordsprocessingapp.entities.Stats;
import com.example.wordsprocessingapp.entities.exceptions.EmptyPayloadException;
import com.example.wordsprocessingapp.entities.exceptions.InputFormatException;
import com.example.wordsprocessingapp.entities.RequestHistory;
import com.example.wordsprocessingapp.repositories.RequestRepository;
import com.example.wordsprocessingapp.repositories.StatsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WordProcessingService {
    private RequestRepository requestRepository;
    private StatsRepository statsRepository;
    private Map<String, Integer> currentStat;

    @Autowired
    public WordProcessingService(RequestRepository requestRepository, StatsRepository statsRepository) {
        this.requestRepository = requestRepository;
        this.statsRepository = statsRepository;
    }

    public WordProcessingService() {
    }


    public Map<String, Integer> proceed(Request request) throws InputFormatException, EmptyPayloadException {
        if(request.getPayload().isEmpty()) throw new EmptyPayloadException("Input cannot be empty!");
        if(isConsistOfNumbers(getWord(request.getPayload()))) throw new
                InputFormatException("Input word cannot consist only of numbers!");
        Map<String, Integer> wordsMap = getWordsMap(request.getPayload());
        log.info("Saving request...");
        requestRepository.save(request);
        log.info("Saving stats...");
        saveAllWordsFromMap(wordsMap, request);
        wordsMap.put("Unique words", wordsMap.size());
        currentStat = wordsMap;
        return wordsMap;
    }

    public RequestHistory getRequestHistory() {
        List<Request> requestList = requestRepository.findAll();
        return new RequestHistory(requestList);
    }

    public Map<String, Integer> getCurrentStat() { return currentStat; }

    public Map<String, Integer> getStatsById(Long id) {
        return statsRepository.findAllByRequest_Id(id)
                .stream()
                .collect(Collectors.toMap(Stats::getWord,
                        Stats::getEntry));
    }

    private void saveAllWordsFromMap(Map<String, Integer> wordsMap, Request request) {
        for(Map.Entry<String, Integer> entry:wordsMap.entrySet()) {
            statsRepository.save(new Stats(entry.getKey(), entry.getValue(), request));
        }
    }

    private Map<String, Integer> getWordsMap(String payload) {
        List<String> wordsList = Arrays.stream(payload.split(" ")).collect(Collectors.toList());
        Map<String, Integer> counterMap = new LinkedHashMap<>();
        for(String word:wordsList) {
            if(!word.isEmpty()) {
                Integer count = counterMap.get(word);
                if(count == null) count = 0;
                counterMap.put(word, ++count);
            }
        }
        return getSortedByEntriesMap(counterMap);
    }

    private static Map<String, Integer> getSortedByEntriesMap(Map<String, Integer> map) {
        Map<String, Integer> resultMap = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(e-> resultMap.put(e.getKey(), e.getValue()));
        return resultMap;
    }

    private String getWord(String payload) {
        return payload.split("\\s")[0];
    }

    private boolean isConsistOfNumbers(String word) {
        return word.matches("[0-9]*");
    }

}
