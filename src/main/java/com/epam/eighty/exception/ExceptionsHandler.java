package com.epam.eighty.exception;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(DataAccessException.class)
    public void handleDataAccessException(final DataAccessException ex) {
        final Logger log = LoggerFactory.getLogger(ExceptionsHandler.class);
        log.error("DataAccessException: " + ex.getMessage());

    }

    @ExceptionHandler(IOException.class)
    public void handleIOException(final IOException ex) {
        final Logger log = LoggerFactory.getLogger(ExceptionsHandler.class);
        log.error("IOException: " + ex.getMessage());

    }

}
