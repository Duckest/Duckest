package ru.duckest.duckest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler({IllegalArgumentException.class, NoSuchElementException.class})
    public ResponseEntity<HttpStatus> invalidResponseParameter() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
