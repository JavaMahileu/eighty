package com.epam.eighty.exception;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionsHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler(DataAccessException.class)
    public void handleDataAccessException(final DataAccessException ex) {
        LOG.error("DataAccessException: " + ex.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public void handleIOException(final IOException ex) {
        LOG.error("IOException: " + ex.getMessage());
    }

}
