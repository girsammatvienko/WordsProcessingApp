package com.example.wordsprocessingapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WordsProcessingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordsProcessingAppApplication.class, args);
    }

}
