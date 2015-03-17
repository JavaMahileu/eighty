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

import com.epam.eighty.domain.Customer;
import com.epam.eighty.resources.TestNeo4jConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestNeo4jConfig.class)
@Transactional
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepo;
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
    public void test_getCustomerByTopicId() throws IOException {
        Long rootTopicId = topicRepo.findBySchemaPropertyValue("title", "root").get().getId();
        List<Customer> customers = customerRepo.getCustomersByTopicId(rootTopicId);
        assertEquals(4, customers.size());
        
        Long subTopicId = topicRepo.findBySchemaPropertyValue("title", "Annotations").get().getId();
        customers = customerRepo.getCustomersByTopicId(subTopicId);
        assertEquals(0, customers.size());
        
        customers = customerRepo.getCustomersByTopicId(9999L);
        assertEquals(0, customers.size());
    }

    @Test
    public void test_getSortedSliceOfCustomersByName() {
        List<Customer> customers = customerRepo.getCustomersMatchingName("stom");
        assertEquals(4, customers.size());
        
        assertEquals("Customer1", customers.get(0).getName());
        assertEquals("Customer2", customers.get(1).getName());
        assertEquals("Customer3", customers.get(2).getName());
        assertEquals("Customer4", customers.get(3).getName());
        
        customers = customerRepo.getCustomersMatchingName("fake");
        assertEquals(0, customers.size());
    }

    @Test
    public void test_getQuestionsNumberInTopicByCustomer() {
        Long rootTopicId = topicRepo.findBySchemaPropertyValue("title", "root").get().getId();
        long questionsNumber = customerRepo.getQuestionsNumberInTopicByCustomer("Customer1", rootTopicId);
        assertEquals(3, questionsNumber);
        
        Long subTopicId = topicRepo.findBySchemaPropertyValue("title", "Annotations").get().getId();
        questionsNumber = customerRepo.getQuestionsNumberInTopicByCustomer("Customer1", subTopicId);
        assertEquals(0, questionsNumber);
        
        questionsNumber = customerRepo.getQuestionsNumberInTopicByCustomer("Customer1", 9999L);
        assertEquals(0, questionsNumber);
    }
    
    @Test
    public void test_checkCountField() {
        Customer customer = customerRepo.findBySchemaPropertyValue("name", "Customer1").get();
        assertEquals(3L, customer.getCount().longValue());
    }

}
