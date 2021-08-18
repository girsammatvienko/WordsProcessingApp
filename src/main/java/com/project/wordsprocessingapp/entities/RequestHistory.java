package com.project.wordsprocessingapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RequestHistory {
    private List<Request> requestList;

}
