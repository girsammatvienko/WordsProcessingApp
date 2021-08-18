package com.example.wordsprocessingapp.controllers.exception;

import com.example.wordsprocessingapp.entities.errors.CustomError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public final ResponseEntity<CustomError> handleException(Exception e) {
        log.info("Exception " + e.getClass().getSimpleName() + " was handled by " + this.getClass().getSimpleName());
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
