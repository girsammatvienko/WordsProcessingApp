package com.example.wordsprocessingapp.services;

import com.example.wordsprocessingapp.entities.Request;
import com.example.wordsprocessingapp.entities.Stats;
import com.example.wordsprocessingapp.entities.exceptions.EmptyPayloadException;
import com.example.wordsprocessingapp.entities.exceptions.InputFormatException;
import com.example.wordsprocessingapp.repositories.RequestRepository;
import com.example.wordsprocessingapp.repositories.StatsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WordProcessingService {
    private RequestRepository requestRepository;
    private StatsRepository statsRepository;

    @Autowired
    public WordProcessingService(RequestRepository requestRepository, StatsRepository statsRepository) {
        this.requestRepository = requestRepository;
        this.statsRepository = statsRepository;
    }

    public WordProcessingService() {
    }

    public void add(Request request) throws EmptyPayloadException, InputFormatException {
        if(request.getPayload().isEmpty()) throw new EmptyPayloadException("Input cannot be empty!");
        if(isConsistOfNumbers(getWord(request.getPayload()))) throw new
                InputFormatException("Input word cannot consist only of numbers!");
        if(isExist(request.getPayload())) {
            String word = getWord(request.getPayload());
            Stats stats = statsRepository.findByWord(word).get();
            stats.setEntry(stats.getEntry() + 1);
            statsRepository.save(stats);
            log.info("Stats was updated");
            log.info("If of updated stats: " + stats.getId());
        }
        else {
            String word = getWord(request.getPayload());
            Stats stats = new Stats(word, 1);
            stats.setRequest(request);
            requestRepository.save(request);
            statsRepository.save(stats);
            log.info("Request was saved in database");
            log.info("Stats was saved in database");
        }
    }

    public Map<String, Integer> getStatistics() {
        log.info("Generating statistics...");
        Map<String, Integer> statistics = getAllStats();
        statistics.put("Unique words", statistics.size());
        return statistics;
    }

    private boolean isExist(String word) {
        return statsRepository.existsByWord(word);
    }

    private LinkedHashMap<String, Integer> getAllStats() {
        LinkedHashMap<String, Integer> statsMap = statsRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Stats::getEntry).reversed())
                .collect(Collectors.toMap(Stats::getWord,
                                          Stats::getEntry,
                                          (v1, v2)-> {throw new AssertionError("Keys should be unique!");},
                                          LinkedHashMap::new));
        return statsMap;
    }

    private String getWord(String payload) {
        return payload.split("\\s")[0];
    }

    private boolean isConsistOfNumbers(String word) {
        return word.matches("[0-9]*");
    }

}
