package com.example.wordsprocessingapp;

import com.example.wordsprocessingapp.controllers.api.MainApiController;
import com.example.wordsprocessingapp.controllers.gui.MainGuiController;
import com.example.wordsprocessingapp.entities.Request;
import com.example.wordsprocessingapp.entities.Stats;
import com.example.wordsprocessingapp.entities.exceptions.EmptyPayloadException;
import com.example.wordsprocessingapp.entities.exceptions.InputFormatException;
import com.example.wordsprocessingapp.repositories.RequestRepository;
import com.example.wordsprocessingapp.repositories.StatsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.awt.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class WordProcessingAppIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainApiController mainApiController;

    @Test
    public void addingValidWordTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Request request = new Request("word");
        mockMvc.perform(post("http://localhost:8080/api/add")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.word").isNumber());
    }

    @Test
    public void addingWordConsistingOnlyOfNumbersAsPayloadTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Request request = new Request("1234");
        mockMvc.perform(post("http://localhost:8080/api/add")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResolvedException()
                        instanceof InputFormatException));
    }

    @Test
    public void addingEmptyWordTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Request request = new Request("");
        mockMvc.perform(post("http://localhost:8080/api/add")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResolvedException()
                        instanceof EmptyPayloadException));
    }

}
