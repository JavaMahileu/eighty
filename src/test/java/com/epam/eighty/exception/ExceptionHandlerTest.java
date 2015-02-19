package com.epam.eighty.exception;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggerFactory.class})
public class ExceptionHandlerTest {

    @InjectMocks
    private ExceptionsHandler exceptionsHandler;

    private static Logger mockLogger;

    @Before
    public void setUp() {
        mockStatic(LoggerFactory.class);
        mockLogger = mock(Logger.class);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(mockLogger);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_handleDataAccessException() throws Exception {
        DataAccessException ex = new DataAccessResourceFailureException("some message");
        exceptionsHandler.handleDataAccessException(ex);
        verify(mockLogger).error("DataAccessException: some message");
    }

    @Test
    public void test_handleIOException() {
        IOException ex = new IOException("some message");
        exceptionsHandler.handleIOException(ex);
        verify(mockLogger).error("IOException: some message");
    }

}