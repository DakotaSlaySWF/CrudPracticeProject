package com.galvanize.CrudPracticeProject.Exeception;


import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class EmployeeControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeControllerAdvice.class);


    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElement(@NotNull Exception ex){
        logger.error("NoSuchElementException: ",ex.getMessage());
        return new ResponseEntity<>("No Such Element Found", HttpStatus.NOT_FOUND);
    }



}
