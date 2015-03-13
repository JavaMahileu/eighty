package com.epam.eighty.exception;

import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletResponse;

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

    @Test
    public void test_handleQuestionNotFoundException() throws Exception {
        HttpServletResponse response = PowerMockito.mock(HttpServletResponse.class);
        QuestionNotFoundException ex = new QuestionNotFoundException(1L, response);
        exceptionsHandler.handleQuestionNotFoundException(ex);
        verify(mockLogger).error("QuestionNotFoundException: Question with id=1 is not found");
    }

    @Test
    public void test_handleTopicNotFoundException_if_topic_is_not_root() {
        HttpServletResponse response = PowerMockito.mock(HttpServletResponse.class);
        TopicNotFoundException ex = new TopicNotFoundException(1L, response);
        exceptionsHandler.handleTopicNotFoundException(ex);
        verify(mockLogger).error("TopicNotFoundException: Topic with id=1 is not found");
    }

    @Test
    public void test_handleTopicNotFoundException_if_topic_is_root() {
        HttpServletResponse response = PowerMockito.mock(HttpServletResponse.class);
        TopicNotFoundException ex = new TopicNotFoundException(response);
        exceptionsHandler.handleTopicNotFoundException(ex);
        verify(mockLogger).error("TopicNotFoundException: Root topic is not found");
    }

}
