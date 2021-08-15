package com.example.wordsprocessingapp.service;

import com.example.wordsprocessingapp.entities.Request;
import com.example.wordsprocessingapp.entities.Stats;
import com.example.wordsprocessingapp.entities.exceptions.EmptyPayloadException;
import com.example.wordsprocessingapp.entities.exceptions.InputFormatException;
import com.example.wordsprocessingapp.repositories.RequestRepository;
import com.example.wordsprocessingapp.repositories.StatsRepository;
import com.example.wordsprocessingapp.services.WordProcessingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.mockito.MockitoAnnotations.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WordProcessingServiceTest {
    @MockBean
    private RequestRepository requestRepository;

    @MockBean
    private StatsRepository statsRepository;

    @Autowired
    private WordProcessingService service;

    @Test
    public void sendingRequestWithValidPayloadTest() throws EmptyPayloadException, InputFormatException {
        Request request = new Request("word");
        service.add(request);
        when(requestRepository.save(request)).thenReturn(request);
        verify(requestRepository, Mockito.times(1)).save(request);
    }

    @Test
    public void sendingRequestWithPayloadConsistingOnlyOfNumbers() throws EmptyPayloadException, InputFormatException {
        Request request = new Request("1234"); // Payload cannot consists only of numbers
        Assertions.assertThrows(InputFormatException.class, () -> {
            service.add(request);
        });
    }

    @Test
    public void sendingRequestWithEmptyPayload() {
        Request request = new Request("");
        Assertions.assertThrows(EmptyPayloadException.class, () -> {
            service.add(request);
        });
    }

    @Test
    public void AddingNewStatsByRequestTest() throws EmptyPayloadException, InputFormatException {
        Request request = new Request("hello");
        service.add(request);
        verify(requestRepository, Mockito.times(1)).save(new Request("hello"));
        verify(statsRepository, Mockito.times(1)).save(new Stats("hello", 1));
    }

    @Test
    public void updatingOfStatsWhenAddingRepeatingWordTest() throws EmptyPayloadException, InputFormatException {
        Request request = new Request("word");
        Stats stats = new Stats("word", 1);
        when(statsRepository.existsByWord("word")).thenReturn(true);
        when(statsRepository.findByWord("word")).thenReturn(Optional.of(stats));
        service.add(request);
        verify(requestRepository, Mockito.times(0)).save(request);
        verify(statsRepository, Mockito.times(1)).save(stats);
    }

    @Test
    public void statisticsMapGenerationTest() {
        List<Stats> statsList = new ArrayList<>();
        Collections.addAll(statsList, new Stats("word", 1),
                new Stats("word1", 2),
                new Stats("word2", 4),
                new Stats("word3", 3));
        when(statsRepository.findAll()).thenReturn(statsList);
        Map<String, Integer> expectedStatisticsMap = new LinkedHashMap<>();
        for(Stats stats:statsList) {
            expectedStatisticsMap.put(stats.getWord(), stats.getEntry());
        }
        expectedStatisticsMap.put("Unique words", statsList.size());
        Assertions.assertEquals(expectedStatisticsMap, service.getStatistics());
    }


}
