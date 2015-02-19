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
    public void handleDataAccessException(DataAccessException ex) {
        final Logger LOG = LoggerFactory.getLogger(ExceptionsHandler.class);
        LOG.error("DataAccessException: " + ex.getMessage());

    }

    @ExceptionHandler(IOException.class)
    public void handleIOException(IOException ex) {
        final Logger LOG = LoggerFactory.getLogger(ExceptionsHandler.class);
        LOG.error("IOException: " + ex.getMessage());

    }

}