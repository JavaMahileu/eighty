package com.epam.eighty.utility;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggerFactory.class})
public class TraceLoggerTest {

    private static Logger mockLogger;

    @InjectMocks
    private TraceLogger logger;

    @Mock
    private JoinPoint point;

    @Mock
    private MethodSignature signature;

    @Mock
    private Object target;


    @Before
    public void setUp() {
        mockStatic(LoggerFactory.class);
        mockLogger = mock(Logger.class);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(mockLogger);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_logWithoutArguments() throws Exception {
        when(point.getTarget()).thenReturn(target);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(mockLogger);
        when(point.getSignature()).thenReturn(signature);
        when(point.getSignature().getName()).thenReturn("Method");
        Object[] arguments = {};
        when(point.getArgs()).thenReturn(arguments);
        logger.log(point);
        verify(mockLogger).trace("Method called");
    }

    @Test
    public void test_logWhenOneArgument() throws Exception {
        when(point.getTarget()).thenReturn(target);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(mockLogger);
        when(point.getSignature()).thenReturn(signature);
        when(point.getSignature().getName()).thenReturn("Method");
        Object[] arguments = {1};
        when(point.getArgs()).thenReturn(arguments);
        logger.log(point);
        verify(mockLogger).trace("Method called with argument: 1");
    }

    @Test
    public void test_logWhenTwoArguments() throws Exception {
        when(point.getTarget()).thenReturn(target);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(mockLogger);
        when(point.getSignature()).thenReturn(signature);
        when(point.getSignature().getName()).thenReturn("Method");
        Object[] arguments = {1, 2};
        when(point.getArgs()).thenReturn(arguments);
        logger.log(point);
        verify(mockLogger).trace("Method called with arguments: 1, 2");
    }

}