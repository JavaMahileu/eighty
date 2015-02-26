package com.epam.eighty.exception;

import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ExceptionsHandler.class})
public class ExceptionHandlerTest {

    @InjectMocks
    private ExceptionsHandler exceptionsHandler;

    @Mock
    private static Logger mockLogger;

    @Before
    public void setUp() throws IllegalArgumentException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        Field field = PowerMockito.field(ExceptionsHandler.class, "LOG");
        field.set(ExceptionsHandler.class, mockLogger);
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
