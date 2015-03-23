package com.epam.eighty.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.epam.eighty.domain.Topic;
import com.epam.eighty.resources.TestNeo4jConfig;

/**
 * @author Aliaksandr_Padalka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestNeo4jConfig.class)
@Transactional
public class TopicRepositoryTest {

    @Autowired
    private TopicRepository topicRepo;

    @Test
    public void test_addTopic() {
        Topic fake = new Topic();
        fake.setTitle("fake title");
        topicRepo.save(fake);

        Optional<Topic> t = topicRepo.findOne(fake.getId());

        assertTrue(t.isPresent());
        assertEquals(t.get(), fake);

        topicRepo.delete(t.get());
    }

    @Test
    public void test_updateTopic() {
        Topic fake = new Topic();
        fake.setTitle("fake title");
        topicRepo.save(fake);

        fake.setTitle("new fake title");
        topicRepo.save(fake);

        Optional<Topic> t = topicRepo.findOne(fake.getId());

        assertTrue(t.isPresent());
        assertEquals(t.get(), fake);

        topicRepo.delete(t.get());
    }

    @Test
    public void test_deleteTopic() {
        Topic fake = new Topic();
        fake.setTitle("fake title");
        topicRepo.save(fake);

        Long id = fake.getId();

        topicRepo.delete(id);

        Optional<Topic> t = topicRepo.findBySchemaPropertyValue("title", fake.getTitle());

        assertFalse(t.isPresent());
    }

    @Test
    public void test_findTopicByTitle() {
        Topic root = new Topic();
        root.setTitle("root");
        topicRepo.save(root);

        Optional<Topic> t = topicRepo.findBySchemaPropertyValue("title", "root");

        assertTrue(t.isPresent());
        assertEquals(t.get(), root);

        topicRepo.delete(t.get());
    }

    @Test
    public void test_findTopicById() {
        Topic fake = new Topic();
        fake.setTitle("fake title");
        topicRepo.save(fake);

        Optional<Topic> t = topicRepo.findOne(fake.getId());

        assertTrue(t.isPresent());
        assertEquals(t.get(), fake);

        topicRepo.delete(t.get());
    }

    @Test
    public void test_getAllTopics() {
        Topic fake1 = new Topic();
        fake1.setTitle("fake topic 1");
        Topic fake2 = new Topic();
        fake2.setTitle("fake topic 2");
        Topic fake3 = new Topic();
        fake3.setTitle("fake topic 3");
        topicRepo.save(fake1);
        topicRepo.save(fake2);
        topicRepo.save(fake3);

        Result<Topic> topics = topicRepo.findAll();
        assertNotNull(topics);
        @SuppressWarnings("unchecked")
        Set<Topic> set = topics.as(Set.class);
        assertNotNull(set);

        topicRepo.delete(fake1.getId());
        topicRepo.delete(fake2.getId());
        topicRepo.delete(fake3.getId());
    }

    @Test
    public void test_getRootTopicsForTopic() {
        Topic root1 = new Topic();
        root1.setTitle("root 1");
        topicRepo.save(root1);
        Topic root2 = new Topic();
        root2.setTitle("root 2");
        topicRepo.save(root2);
        Topic root3 = new Topic();
        root3.setTitle("root 3");
        topicRepo.save(root3);

        Topic topic = new Topic();
        topic.setTitle("topic");
        topicRepo.save(topic);

        root1.getTopics().add(root2);
        topicRepo.save(root1);
        root2.getTopics().add(root3);
        root2.getTopics().add(topic);
        topicRepo.save(root2);

        List<Topic> list = new ArrayList<>();
        list.add(root2);
        list.add(root1);

        List<Topic> result = topicRepo.getRootTopicsForTopic(topic.getId()).getContent();

        assertNotNull(result);
        assertEquals(result.size(), 3);

        topicRepo.delete(topic);
        topicRepo.delete(root3);
        topicRepo.delete(root2);
        topicRepo.delete(root1);
    }
}
