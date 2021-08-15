package com.example.wordsprocessingapp.controllers.exception;

import com.example.wordsprocessingapp.entities.exceptions.CustomError;
import com.example.wordsprocessingapp.entities.exceptions.EmptyPayloadException;
import com.example.wordsprocessingapp.entities.exceptions.InputFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.awt.im.InputContext;
import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public final ResponseEntity<CustomError> handleException(Exception e) {
        String message = e.getMessage();
        Date date = new Date();
        CustomError error = CustomError.builder()
                            .message(message)
                            .dateOfError(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").
                                    format(date))
                            .build();
        HttpStatus status = HttpStatus.valueOf(500);
        return new ResponseEntity<CustomError>(error, status);
    }


}
