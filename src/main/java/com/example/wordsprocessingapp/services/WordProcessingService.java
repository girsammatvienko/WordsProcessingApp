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

//    public void add(Request request) throws EmptyPayloadException, InputFormatException {
//       getNumberOfUniqueWords(request.getPayload());
//        if(request.getPayload().isEmpty()) throw new EmptyPayloadException("Input cannot be empty!");
//        if(isConsistOfNumbers(getWord(request.getPayload()))) throw new
//                InputFormatException("Input word cannot consist only of numbers!");
//        if(isExist(request.getPayload())) {
//            String word = getWord(request.getPayload());
//            Stats stats = statsRepository.findByWord(word).get();
//            stats.setEntry(stats.getEntry() + 1);
//            statsRepository.save(stats);
//            log.info("Stats was updated");
//            log.info("If of updated stats: " + stats.getId());
//        }
//        else {
//            String word = getWord(request.getPayload());
//            Stats stats = new Stats(word, 1);
//            stats.setRequest(request);
//            requestRepository.save(request);
//            statsRepository.save(stats);
//            log.info("Request was saved in database");
//            log.info("Stats was saved in database");
//        }
//    }

    public Map<String, Integer> proceed(Request request) throws InputFormatException, EmptyPayloadException {
        if(request.getPayload().isEmpty()) throw new EmptyPayloadException("Input cannot be empty!");
        if(isConsistOfNumbers(getWord(request.getPayload()))) throw new
                InputFormatException("Input word cannot consist only of numbers!");
        Map<String, Integer> wordsMap = getWordsMap(request.getPayload());
        requestRepository.save(request);
        saveAllWordsFromMap(wordsMap, request);
        wordsMap.put("Unique words", wordsMap.size());
        return wordsMap;
    }

    private void saveAllWordsFromMap(Map<String, Integer> wordsMap, Request request) {
        for(Map.Entry<String, Integer> entry:wordsMap.entrySet()) {
            statsRepository.save(new Stats(entry.getKey(), entry.getValue(), request));
        }
    }

    private List<Stats> convertStatsMapToList(Map<String, Integer> statsMap) {
        List<Stats> resultList = new ArrayList<>();
        for(Map.Entry<String, Integer> e:statsMap.entrySet()) {
            resultList.add(new Stats(e.getKey(), e.getValue()));
        }
        return resultList;
    }

    public Map<String, Integer> generateStatisticsMap(Request request) {
        Map<String, Integer> statisticsMap = getSortedByEntriesMap(getWordsMap(request.getPayload()));
        statisticsMap.put("Unique words", countUniqueWordsAmount());
        return statisticsMap;
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

    public Integer countUniqueWordsAmount() {
        return statsRepository.findAll().size();
    }

    private static Map<String, Integer> getSortedByEntriesMap(Map<String, Integer> map) {
        Map<String, Integer> resultMap = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(e-> resultMap.put(e.getKey(), e.getValue()));
        return resultMap;
    }

//    public Map<String, Integer> getStatistics() {
//        log.info("Generating statistics...");
//        Map<String, Integer> statistics = getAllStats();
//        statistics.put("Unique words", statistics.size());
//        return statistics;
//    }

    private boolean isExist(String word) {
        return statsRepository.existsByWord(word);
    }

    public Map<String, Integer> getStatsById(Long id) {
        return statsRepository.findAllByRequest_Id(id)
                .stream()
                .collect(Collectors.toMap(Stats::getWord,
                        Stats::getEntry));
    }

//    private LinkedHashMap<String, Integer> getAllStats() {
//        LinkedHashMap<String, Integer> statsMap = statsRepository.findAll()
//                .stream()
//                .sorted(Comparator.comparingInt(Stats::getEntry).reversed())
//                .collect(Collectors.toMap(Stats::getWord,
//                        Stats::getEntry,
//                        (v1, v2)-> {throw new AssertionError("Keys should be unique!");},
//                        LinkedHashMap::new));
//        return statsMap;
//    }

    private String getWord(String payload) {
        return payload.split("\\s")[0];
    }

    private boolean isConsistOfNumbers(String word) {
        return word.matches("[0-9]*");
    }

}
