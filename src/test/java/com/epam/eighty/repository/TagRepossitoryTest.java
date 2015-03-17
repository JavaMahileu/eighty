package com.epam.eighty.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

import com.epam.eighty.domain.Tag;
import com.epam.eighty.resources.TestNeo4jConfig;

/**
 * Created by Aliaksandr_Padalka on 23/07/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestNeo4jConfig.class)
@Transactional
public class TagRepossitoryTest {

    @Autowired
    private TagRepository tagRepo;
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
    public void test_getTagsByTopicId() {
        Long rootTopicId = topicRepo.findBySchemaPropertyValue("title", "root").get().getId();
        List<Tag> tags = tagRepo.getTagsByTopicId(rootTopicId);
        assertEquals(9, tags.size());
        
        Long subTopicId = topicRepo.findBySchemaPropertyValue("title", "Annotations").get().getId();
        tags = tagRepo.getTagsByTopicId(subTopicId);
        assertEquals(0, tags.size());
        
        tags = tagRepo.getTagsByTopicId(9999L);
        assertEquals(0, tags.size());
    }

    @Test
    public void test_getTopNFromAllTags() {
        List<Tag> tags = tagRepo.getTopNFromAllTags(3L);
        assertEquals(3, tags.size());
        
        assertEquals("class", tags.get(0).getTag());
        assertEquals("variable", tags.get(1).getTag());
        assertEquals("object", tags.get(2).getTag());
        
        tags = tagRepo.getTopNFromAllTags(1L);
        assertEquals(1, tags.size());
    }

    @Test
    public void test_RemoveTagsWithoutQuestions() {
        Tag faketag = new Tag();
        faketag.setTag("fake tag");
        tagRepo.save(faketag);
        assertTrue(tagRepo.findBySchemaPropertyValue("tag", "fake tag").isPresent());
        
        tagRepo.removeTagsWithoutQuestion();
        assertFalse(tagRepo.findBySchemaPropertyValue("tag", "fake tag").isPresent());
    }

    @Test
    public void test_getSortedSetOfTagsByName() {
        List<Tag> tags = tagRepo.getSortedTagsMatchingName("o");
        assertEquals(4, tags.size());
        
        assertEquals("double", tags.get(0).getTag());
        assertEquals("float", tags.get(1).getTag());
        assertEquals("object", tags.get(2).getTag());
        assertEquals("singleton", tags.get(3).getTag());
        
        tags = tagRepo.getSortedTagsMatchingName("fake tag");
        assertEquals(0, tags.size());
    }

    @Test
    public void test_getQuestionsInTopicByTag() {
        Long rootTopicId = topicRepo.findBySchemaPropertyValue("title", "root").get().getId();
        long questionsNumber = tagRepo.getQuestionsNumberInTopicByTag("variable", rootTopicId);
        assertEquals(3, questionsNumber);
        
        Long subTopicId = topicRepo.findBySchemaPropertyValue("title", "Annotations").get().getId();
        questionsNumber = tagRepo.getQuestionsNumberInTopicByTag("variable", subTopicId);
        assertEquals(0, questionsNumber);
        
        questionsNumber = tagRepo.getQuestionsNumberInTopicByTag("fake tag", rootTopicId);
        assertEquals(0, questionsNumber);
    }

}
