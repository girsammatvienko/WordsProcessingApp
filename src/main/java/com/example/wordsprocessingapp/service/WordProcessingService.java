package com.example.wordsprocessingapp.service;

import com.example.wordsprocessingapp.entities.Request;
import com.example.wordsprocessingapp.entities.Stats;
import com.example.wordsprocessingapp.entities.exceptions.EmptyPayloadException;
import com.example.wordsprocessingapp.entities.exceptions.InputFormatException;
import com.example.wordsprocessingapp.repository.RequestRepository;
import com.example.wordsprocessingapp.repository.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WordProcessingService {
    private RequestRepository requestRepository;
    private StatsRepository statsRepository;

    @Autowired
    public WordProcessingService(RequestRepository requestRepository, StatsRepository statsRepository) {
        this.requestRepository = requestRepository;
        this.statsRepository = statsRepository;
    }


    public void add(Request request) throws EmptyPayloadException, InputFormatException {
        if(request.getPayload().isEmpty()) throw new EmptyPayloadException();
        if(isConsistOfNumbers(getWord(request.getPayload()))) throw new InputFormatException();
        if(isExist(request.getPayload())) {
            String word = getWord(request.getPayload());
            Stats stats = statsRepository.findByWord(word);
            stats.setEntry(stats.getEntry() + 1);
            statsRepository.save(stats);
        }
        else {
            String word = getWord(request.getPayload());
            Stats stats = new Stats(word, 1);
            stats.setRequest(request);
            requestRepository.save(request);
            statsRepository.save(stats);
        }
    }

    private boolean isExist(String word) {
        return statsRepository.existsByWord(word);
    }

    public Map<String, Integer> getStatistics() {
        Map<String, Integer> statistics = convertListToMap(getAllStats());
        statistics = getSortedByEntriesMap(statistics);
        statistics.put("Unique words", statistics.size());
        return statistics;
    }

    private Map<String, Integer> getSortedByEntriesMap(Map<String, Integer> mapToSort) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(mapToSort.entrySet());
        list.sort(Map.Entry.comparingByValue(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        }));
        Map<String, Integer> result = new LinkedHashMap();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private Map<String, Integer> convertListToMap(List<Stats> statsList) {
        Map<String, Integer> statsMap = new LinkedHashMap<>();
        for(Stats stats:statsList) {
            statsMap.put(stats.getWord(), stats.getEntry());
        }
        return statsMap;
    }

    private List<Stats> getAllStats() {
        return statsRepository.findAll();
    }

    private String getWord(String payload) {
        return payload.split("\\s")[0];
    }

    private boolean isConsistOfNumbers(String word) {
        return word.matches("[0-9]*");
    }

}
