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
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.epam.eighty.domain.Question;
import com.epam.eighty.resources.TestNeo4jConfig;

/**
 * @author Aliaksandr_Padalka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestNeo4jConfig.class)
@Transactional
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepo;
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
    public void test_getQuestionsByTopicId() {
        Long rootTopicId = topicRepo.findBySchemaPropertyValue("title", "root").get().getId();
        Slice<Question> questions = questionRepo.getQuestionsByTopicId(rootTopicId, null);
        assertEquals(6, questions.getContent().size());
        
        Long subTopicId = topicRepo.findBySchemaPropertyValue("title", "Annotations").get().getId();
        questions = questionRepo.getQuestionsByTopicId(subTopicId, null);
        assertEquals(0, questions.getContent().size());
        
        questions = questionRepo.getQuestionsByTopicId(9999L, null);
        assertEquals(0, questions.getContent().size());
    }

    @Test
    public void test_getQuestionsByTopicIdAndTag() {
        Long rootTopicId = topicRepo.findBySchemaPropertyValue("title", "root").get().getId();
        List<Question> questions = questionRepo.getQuestionsByTopicIdAndTag(rootTopicId, "object");
        assertEquals(2, questions.size());
        
        questions = questionRepo.getQuestionsByTopicIdAndTag(rootTopicId, "fakeTag");
        assertEquals(0, questions.size());
        
        Long subTopicId = topicRepo.findBySchemaPropertyValue("title", "Annotations").get().getId();
        questions = questionRepo.getQuestionsByTopicIdAndTag(subTopicId, "object");
        assertEquals(0, questions.size());
    }

    @Test
    public void test_getQuestionsByTag() {
        List<Question> questions = questionRepo.getQuestionsByTag("variable");
        assertEquals(3, questions.size());
        
        questions = questionRepo.getQuestionsByTag("fakeTag");
        assertEquals(0, questions.size());
    }

    @Test
    public void test_getQuestionsByCustomerName() {
        List<Question> questions = questionRepo.getQuestionsByCustomerName("Customer1");
        assertEquals(3, questions.size());
        
        questions = questionRepo.getQuestionsByCustomerName("FakeCustomer");
        assertEquals(0, questions.size());
    }
}
