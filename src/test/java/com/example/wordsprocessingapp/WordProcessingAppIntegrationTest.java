package com.example.wordsprocessingapp;

import com.example.wordsprocessingapp.controllers.api.MainApiController;
import com.example.wordsprocessingapp.controllers.exception.RestResponseEntityExceptionHandler;
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
import com.google.gson.JsonObject;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.json.*;
import java.awt.*;
import java.util.Iterator;

import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WordProcessingAppIntegrationTest {
    private MockMvc mockMvc;

    @Autowired
    private MainApiController mainApiController;

    @Autowired
    private StatsRepository statsRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mainApiController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void addingValidWordTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Request request = new Request("word1");
        MvcResult result = mockMvc.perform(post("http://localhost:8080/api/add")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                       .andReturn();
        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("\"" + request.getPayload() + "\""));
    }

    @Test
    public void addingWordConsistingOnlyOfNumbersAsPayloadTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Request request = new Request("1234");
        mockMvc.perform(post("http://localhost:8080/api/add")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResolvedException()
                        instanceof InputFormatException))
                .andExpect(result -> assertEquals("Input word cannot consist only of numbers!",
                        result.getResolvedException().getMessage()));;
    }

    @Test
    public void addingEmptyWordTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Request request = new Request("");
        mockMvc.perform(post("http://localhost:8080/api/add")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResolvedException()
                        instanceof EmptyPayloadException))
                .andExpect(result -> assertEquals("Input cannot be empty!",
                result.getResolvedException().getMessage()));;
    }

}
