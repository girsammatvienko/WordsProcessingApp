package com.example.wordsprocessingapp.entities;

import com.example.wordsprocessingapp.entities.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class RequestHistory {
    private List<Request> requestList;

}
