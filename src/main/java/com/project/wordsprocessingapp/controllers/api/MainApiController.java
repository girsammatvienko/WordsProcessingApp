package com.project.wordsprocessingapp.controllers.api;

import com.project.wordsprocessingapp.entities.Request;
import com.project.wordsprocessingapp.entities.exceptions.EmptyPayloadException;
import com.project.wordsprocessingapp.entities.exceptions.InputFormatException;
import com.project.wordsprocessingapp.services.WordProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class MainApiController {
    private WordProcessingService service;

    @Autowired
    public MainApiController(WordProcessingService service) {
        this.service = service;
    }

    public MainApiController() {}

    @PostMapping(value = "/proceed", consumes = "application/json")
    public Map<String, Integer> evaluateSentence(@RequestBody Request request) throws EmptyPayloadException, InputFormatException {
        return service.proceed(request);
    }

    @GetMapping(value = "/getStats/{id}")
    public Map<String, Integer> getAllStats(@PathVariable Long id) {
        return service.getStatsById(id);
    }




}
