package com.example.wordsprocessingapp.service;

import com.example.wordsprocessingapp.entities.Request;
import com.example.wordsprocessingapp.entities.Stats;
import com.example.wordsprocessingapp.repository.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordProcessingService {
    private StatsRepository statsRepository;

    @Autowired
    public WordProcessingService(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public void addEntry(Request request) {
        if(isExist(request.getPayload())) {
            Stats stats = statsRepository.findByWord(request.getPayload());
            stats.setEntry(stats.getEntry() + 1);
            stats.setRequest(request);
            statsRepository.save(stats);
        }
        else {
            Stats stats = new Stats(request.getPayload(), 1, request);
            statsRepository.save(stats);
        }
    }

    private boolean isExist(String word) {
        return statsRepository.existsByWord(word);
    }

    public List<Stats> getAll() {
        return statsRepository.findAll();
    }

}
