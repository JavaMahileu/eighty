package com.epam.eighty.service;

import com.epam.eighty.domain.Topic;
import com.epam.eighty.repository.TopicRepository;
import com.epam.eighty.service.impl.TopicServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.template.Neo4jOperations;

import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Aliaksandr_Padalka
 */
@RunWith(MockitoJUnitRunner.class)
public class TopicServiceTest {

    private Optional<Topic> fake;
    private Optional<Topic> root;
    private Result<Topic> results;
    private Set<Topic> fakes;
    private List<Topic> list;

    @Mock
    private TopicRepository topicRepo;

    @Mock
    private Neo4jOperations template;

    @InjectMocks
    private TopicServiceImpl topicService;

    @Before
    public void setUp() {
        root = Optional.of(new Topic());
        root.get().setTitle("root");
        root.get().setId(0L);

        fake = Optional.of(new Topic());
        fake.get().setTitle("fake title");
        fake.get().setId(1000L);

        Topic fake0 = new Topic();
        fake0.setTitle("fake title 0");
        fake0.setId(10L);

        Topic fake1 = new Topic();
        fake1.setTitle("fake title 1");
        fake1.setId(11L);

        Topic fake2 = new Topic();
        fake2.setTitle("fake title 2");
        fake2.setId(12L);

        fakes = new HashSet<>();

        Set<Topic> set = new HashSet<>();
        set.add(fake1);
        set.add(fake2);
        fake0.setTopics(set);

        fakes.add(fake0);
        fakes.add(fake1);
        fakes.add(fake2);

        results = new QueryResultBuilder<>(fakes);

        list = new ArrayList<>();
        list.add(fake0);
        list.add(fake1);
        list.add(fake2);
    }

    @Test
    public void test_getAllTopics() {
        when(topicRepo.findAll()).thenReturn(results);

        Set<Topic> set = topicService.getAllTopics();

        assertNotNull(set);
        assertEquals(set, fakes);
    }

    @Test
    public void test_updateTopic() {
        topicService.updateTopic(fake.get());
        verify(topicRepo).save(fake.get());
    }

    @Test
    public void test_deleteTopic() {
        topicService.deleteTopic(fake.get().getId());
        verify(topicRepo).delete(fake.get().getId());
    }

    @Test
    public void test_createTopic() {
        when(topicRepo.findOne(root.get().getId())).thenReturn(root);
        topicService.createTopic(fake.get(), root.get().getId());
        verify(topicRepo).save(root.get());
    }

    @Test
    public void test_getTopicById() {
        when(topicRepo.findOne(fake.get().getId())).thenReturn(fake);
        Topic topic = topicService.getTopicById(fake.get().getId()).get();
        assertNotNull(topic);
        assertEquals(topic, fake.get());
    }

    @Test
    public void test_getRoot() {
        when(topicRepo.findBySchemaPropertyValue("title", "root")).thenReturn(root);
        Topic topic = topicService.getRoot().get();
        assertNotNull(topic);
        assertEquals(topic, root.get());
    }

}
