package com.example.wordsprocessingapp.controllers.api;

import com.example.wordsprocessingapp.entities.Request;
import com.example.wordsprocessingapp.entities.exceptions.EmptyPayloadException;
import com.example.wordsprocessingapp.entities.exceptions.InputFormatException;
import com.example.wordsprocessingapp.services.WordProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MainApiController {
    private WordProcessingService service;

    @Autowired
    public MainApiController(WordProcessingService service) {
        this.service = service;
    }

    public MainApiController() {}

    @PostMapping(value = "/add", consumes = "application/json")
    public Map<String, Integer> proceed(@RequestBody Request request) throws EmptyPayloadException, InputFormatException {
        service.add(request);
        Map<String, Integer> statistics = service.getStatistics();
        return statistics;
    }
}
