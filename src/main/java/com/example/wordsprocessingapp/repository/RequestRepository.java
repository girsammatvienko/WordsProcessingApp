package com.example.wordsprocessingapp.repository;

import com.example.wordsprocessingapp.entities.Request;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {
}
