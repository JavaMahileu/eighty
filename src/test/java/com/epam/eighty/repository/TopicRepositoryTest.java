package com.epam.eighty.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    private String createCypherScript;

    private String deleteScript = "START n=node(*) OPTIONAL MATCH (n)-[r]-() delete n,r;";

    @Before
    public void prepareTestDatabase() throws IOException {
        engine.execute(createCypherScript);
    }

    @After
    public void cleanTestDatabase() {
        engine.execute(deleteScript);
    }

    @Test
    public void test_getRootTopicsForTopic() {
        Long topicId = topicRepo.findBySchemaPropertyValue("title", "Annotations").get().getId();

        List<Topic> result = topicRepo.getRootTopicsForTopic(topicId).getContent();

        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals("Annotations", result.get(0).getTitle());
        assertEquals("Language basics", result.get(1).getTitle());
        assertEquals("Java", result.get(2).getTitle());
        assertEquals("Programming Languages", result.get(3).getTitle());
        assertEquals("root", result.get(4).getTitle());

    }
}
