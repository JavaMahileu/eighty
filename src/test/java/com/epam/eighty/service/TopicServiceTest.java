package com.epam.eighty.service;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.template.Neo4jOperations;

import com.epam.eighty.domain.Topic;
import com.epam.eighty.exception.TopicNotFoundException;
import com.epam.eighty.repository.TopicRepository;
import com.epam.eighty.service.impl.TopicServiceImpl;

/**
 * @author Aliaksandr_Padalka
 */
@RunWith(MockitoJUnitRunner.class)
public class TopicServiceTest {

    private Topic topic;
    private Topic root;
    private Result<Topic> results;
    private List<Topic> topics;

    @Mock
    private TopicRepository topicRepo;

    @Mock
    private Neo4jOperations template;

    @InjectMocks
    private TopicServiceImpl topicService;

    @Before
    public void setUp() {
        root = new Topic();
        root.setTitle("root");
        root.setId(0L);

        topic = new Topic();
        topic.setTitle("fake title");
        topic.setId(1000L);

        Topic topic0 = new Topic();
        topic0.setTitle("fake title 0");
        topic0.setId(10L);

        Topic topic1 = new Topic();
        topic1.setTitle("fake title 1");
        topic1.setId(11L);

        Topic topic2 = new Topic();
        topic2.setTitle("fake title 2");
        topic2.setId(12L);

        topics = new ArrayList<>();
        topics.add(topic0);
        topics.add(topic1);
        topics.add(topic2);

        results = new QueryResultBuilder<>(topics);
    }

    @Test
    public void test_getAllTopics() {
        when(topicRepo.findAll()).thenReturn(results);

        List<Topic> actualTopics = topicService.getAllTopics();

        assertThat(actualTopics, contains(topics.toArray()));
    }

    @Test
    public void test_updateTopic() {
        topicService.updateTopic(root);
        
        verify(topicRepo).save(root);
    }

    @Test
    public void test_deleteTopic() {
        topicService.deleteTopic(topic.getId());
        
        verify(topicRepo).delete(topic.getId());
    }

    @Test
    public void test_createTopic() {
        when(topicRepo.findOne(root.getId())).thenReturn(Optional.of(root));

        topicService.createTopic(topic, root.getId());

        verify(topicRepo).save(root);
        assertThat(root.getTopics(), contains(topic));
    }

    @Test(expected = TopicNotFoundException.class)
    public void test_createTopicNegative() {
        when(topicRepo.findOne(root.getId())).thenReturn(Optional.empty());

        topicService.createTopic(topic, root.getId());
    }

    @Test
    public void test_getTopicById() {
        when(topicRepo.findOne(topic.getId())).thenReturn(Optional.of(topic));

        Topic actualTopic = topicService.getTopicById(topic.getId());

        assertEquals(actualTopic, topic);
    }

    @Test(expected = TopicNotFoundException.class)
    public void test_getTopicByIdNegative() {
        when(topicRepo.findOne(topic.getId())).thenReturn(Optional.empty());

        topicService.getTopicById(topic.getId());
    }

    @Test
    public void test_getRoot() {
        when(topicRepo.findBySchemaPropertyValue("title", "root")).thenReturn(Optional.of(root));
        
        Optional<Topic> actualTopic = topicService.getRoot();
        
        assertEquals(actualTopic.get(), root);
    }

}
