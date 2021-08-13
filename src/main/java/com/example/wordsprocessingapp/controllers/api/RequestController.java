package com.example.wordsprocessingapp.controllers.api;

import com.example.wordsprocessingapp.entities.Request;
import com.example.wordsprocessingapp.entities.Stats;
import com.example.wordsprocessingapp.service.WordProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RequestController {
    private WordProcessingService service;

    @Autowired
    public RequestController(WordProcessingService service) {
        this.service = service;
    }

    @PostMapping(value = "/proceed", consumes = "application/json")
    public List<Stats> proceed(@RequestBody Request request) {
        service.addEntry(request);
        List<Stats> allStats = service.getAll();
        return allStats;
    }
}
