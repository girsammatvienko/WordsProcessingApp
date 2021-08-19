package com.project.wordsprocessingapp.service;

import com.project.wordsprocessingapp.entities.Request;
import com.project.wordsprocessingapp.entities.Stats;
import com.project.wordsprocessingapp.entities.exceptions.EmptyPayloadException;
import com.project.wordsprocessingapp.entities.exceptions.InputFormatException;
import com.project.wordsprocessingapp.repositories.RequestRepository;
import com.project.wordsprocessingapp.repositories.StatsRepository;
import com.project.wordsprocessingapp.services.WordProcessingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WordProcessingServiceTest {
    @Mock
    private RequestRepository requestRepository;

    @Mock
    private StatsRepository statsRepository;

    @InjectMocks
    private WordProcessingService service;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void sendingRequestWithValidPayloadTest() throws EmptyPayloadException, InputFormatException {
        Request request = new Request("word");
        service.proceed(request);
        when(requestRepository.save(request)).thenReturn(request);
        verify(requestRepository, Mockito.times(1)).save(request);
    }

    @Test
    public void sendingRequestWithPayloadConsistingOnlyOfNumbers() throws EmptyPayloadException, InputFormatException {
        Request request = new Request("123456");
        Assertions.assertThrows(InputFormatException.class, () -> {
            service.proceed(request);
        });
    }

    @Test
    public void sendingRequestWithEmptyPayload() {
        Request request = new Request("");
        when(requestRepository.save(request)).thenReturn(request);
        Assertions.assertThrows(EmptyPayloadException.class, () -> {
            service.proceed(request);
        });
    }

    @Test
    public void AddingNewStatsByRequestTest() throws EmptyPayloadException, InputFormatException {
        Request request = new Request("hello");
        service.proceed(request);
        when(requestRepository.save(request)).thenReturn(request);
        verify(requestRepository, Mockito.times(1)).save(new Request("hello"));
        verify(statsRepository, Mockito.times(1)).save(new Stats("hello", 1));
    }

    private Map<String, Integer> createExpectedStatisticsForStatsList(List<Stats> statsList) {
        Map<String, Integer> result = new LinkedHashMap<>();
        for(Stats stats:statsList) {
            result.put(stats.getWord(), stats.getEntry());
        }
        result.put("Unique words", statsList.size());
        return result;
    }


}
