package com.epam.eighty.web.api;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.eighty.domain.Topic;
import com.epam.eighty.service.DBPopulatorService;
import com.epam.eighty.service.TopicService;
import com.epam.eighty.exception.TopicNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

public class TopicControllerTest {

    private static final long TEST_LONG = 1L;

    @InjectMocks
    private TopicController topicController;

    @Mock
    private TopicService topicService;

    @Mock
    private DBPopulatorService dbService;

    @Mock
    private Topic topic;
    @Mock
    private List<Topic> list ;

    @Mock
    private HttpServletResponse response;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_getTopic_successful_case_with_existing_topic() {
        when(topicService.getTopicById(TEST_LONG)).thenReturn(topic);
        assertTrue(topicController.getTopic(TEST_LONG).equals(topic));
    }

    @Test
    public void test_getFullTopic_successful_case_with_existing_topic() {
        when(topicService.getFullTopicById(TEST_LONG)).thenReturn(topic);
        assertTrue(topicController.getFullTopic(TEST_LONG).equals(topic));
    }

    @Test
    public void test_getRootTopic_successful_case_with_existing_root() throws IOException {
        Optional <Topic> optionalTopic = Optional.ofNullable(topic);
        when(topicService.getRoot()).thenReturn(optionalTopic);
        assertTrue(topicController.getRootTopic().equals(topic));
    }

    @Test
    public void test_getRootTopic_successful_case_with_not_existing_root() throws IOException {
        Optional <Topic> optionalTopic = Optional.ofNullable(topic);
        Optional <Topic> optionalEmpty = Optional.empty();
        when(topicService.getRoot()).thenReturn(optionalEmpty).thenReturn(optionalTopic);
        assertTrue(topicController.getRootTopic().equals(topic));
        verify(dbService, Mockito.times(1)).populate();
    }

    @Test(expected = TopicNotFoundException.class)
    public void test_getRootTopic_unsuccessful_case_with_not_existing_root() throws IOException {
        Optional <Topic> optionalEmpty = Optional.empty();
        when(topicService.getRoot()).thenReturn(optionalEmpty).thenReturn(optionalEmpty);
        topicController.getRootTopic().equals(topic);
    }

    @Test
    public void test_deleteTopic() {
        topicController.deleteTopic(TEST_LONG, response);
        verify(topicService, Mockito.times(1)).deleteTopic(TEST_LONG);
        verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void test_updateTopic() {
        topicController.updateTopic(topic, response);
        verify(topicService, Mockito.times(1)).updateTopic(topic);
        verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void test_createTopic() {
        topicController.createTopic(topic, TEST_LONG, response);
        verify(topicService, Mockito.times(1)).createTopic(topic, TEST_LONG);
        verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void test_getPath() {
        when(topicService.getRootTopicsForTopic(5L)).thenReturn(list);
        assertTrue(topicController.getPath(5L).equals(list));
    }
}
