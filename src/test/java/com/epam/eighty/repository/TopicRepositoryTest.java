package com.epam.eighty.repository;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired
    private ExecutionEngine engine;
    
    @Autowired
    private String creatCypherScript;
    
    private String deleteScript = "START n=node(*) OPTIONAL MATCH (n)-[r]-() delete n,r;";

    @Before
    public void prepareTestDatabase() throws IOException {
        engine.execute(creatCypherScript);
    }

    @After
    public void cleanTestDatabase() {
        engine.execute(deleteScript);
    }

    @Test
    public void test_getRootTopicsForTopic() {
        Long subTopicId = topicRepo.findBySchemaPropertyValue("title", "Annotations").get().getId();
        List<Topic> topics = topicRepo.getRootTopicsForTopic(subTopicId);
        assertEquals(4, topics.size());
        
        Long rootTopicId = topicRepo.findBySchemaPropertyValue("title", "root").get().getId();
        topics = topicRepo.getRootTopicsForTopic(rootTopicId);
        assertEquals(0, topics.size());
        
        topics = topicRepo.getRootTopicsForTopic(99999L);
        assertEquals(0, topics.size());
    }
}
