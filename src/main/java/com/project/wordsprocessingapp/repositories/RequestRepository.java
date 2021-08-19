package com.project.wordsprocessingapp.repositories;

import com.project.wordsprocessingapp.entities.Request;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {
    Boolean existsByPayload(String payload);
    List<Request> findAll();
}
