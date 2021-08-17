package com.example.wordsprocessingapp.repository;

import com.example.wordsprocessingapp.entities.Stats;
import com.example.wordsprocessingapp.repositories.StatsRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StatsRepositoryTest {
    @Autowired
    private StatsRepository statsRepository;

    @Test
    public void savingAndFindingStatsByWordTest() {
        String word = "word";
        Stats stats = new Stats(word, 3);
        statsRepository.save(stats);

        boolean isExist = statsRepository.existsByWord(word);
        Assert.assertTrue(isExist);
    }

    @Test
    public void findingAllTest() {
        List<Stats> statsList = statsRepository.findAll();
        Assert.assertNotNull(statsList);
    }

}
