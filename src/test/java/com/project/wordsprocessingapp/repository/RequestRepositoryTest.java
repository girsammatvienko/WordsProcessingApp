package com.project.wordsprocessingapp.repository;

import com.project.wordsprocessingapp.entities.Request;
import com.project.wordsprocessingapp.repositories.RequestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RequestRepositoryTest {
    @Autowired
    private RequestRepository requestRepository;

    @Test
    public void SavingAndFindingRequestByPayloadTest() {
        var request = new Request();
        String payload = "word";
        request.setPayload(payload);
        requestRepository.save(request);

        boolean isExist = requestRepository.existsByPayload(payload);
        assertTrue(isExist);
    }

}
