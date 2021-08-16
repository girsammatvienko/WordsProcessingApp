package com.example.wordsprocessingapp.repositories;

import com.example.wordsprocessingapp.entities.Request;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {
    Boolean existsByPayload(String payload);
}
