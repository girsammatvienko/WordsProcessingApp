package com.example.wordsprocessingapp.repositories;

import com.example.wordsprocessingapp.entities.Stats;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatsRepository extends CrudRepository<Stats, Long> {
    List<Stats> findAll();
    Boolean existsByWord(String word);
    Optional<Stats> findByWord(String word);
}
