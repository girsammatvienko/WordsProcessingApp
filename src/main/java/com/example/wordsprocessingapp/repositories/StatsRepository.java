package com.example.wordsprocessingapp.repositories;

import com.example.wordsprocessingapp.entities.Stats;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatsRepository extends CrudRepository<Stats, Long> {
    List<Stats> findAll();
    Boolean existsByWord(String word);
    Optional<Stats> findByWord(String word);
    List<Stats> findAllByRequest_Id(Long id);
}
