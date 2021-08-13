package com.example.wordsprocessingapp.repository;

import com.example.wordsprocessingapp.entities.Stats;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatsRepository extends CrudRepository<Stats, Long> {
    List<Stats> findAll();
    Boolean existsByWord(String word);
    Stats findByWord(String word);
}
