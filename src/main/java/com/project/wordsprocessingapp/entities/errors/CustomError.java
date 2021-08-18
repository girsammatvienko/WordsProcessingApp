package com.project.wordsprocessingapp.entities.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomError {
    private String message;
    private String dateOfError;
}
