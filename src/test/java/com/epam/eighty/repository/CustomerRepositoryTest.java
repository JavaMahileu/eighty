package com.epam.eighty.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.epam.eighty.domain.Customer;
import com.epam.eighty.domain.Question;
import com.epam.eighty.domain.Topic;
import com.epam.eighty.resources.TestNeo4jConfig;
import com.epam.eighty.utility.Converter;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestNeo4jConfig.class)
@Transactional
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private TopicRepository topicRepo;
    @Autowired
    private QuestionRepository questionRepo;
    
    @Test
    public void test_getCustomerByTopicId() {
        Topic root = new Topic();
        root.setTitle("root");

        Question q1 = new Question();
        q1.setQuestion("q1");
        Question q2 = new Question();
        q2.setQuestion("q2");

        Customer customer1 = new Customer();
        customer1.setName("c1");
        Customer customer2 = new Customer();
        customer2.setName("c2");

        Set<Customer> customers1 = new HashSet<Customer>();
        customers1.add(customer1);
        Set<Customer> customers2 = new HashSet<Customer>();
        customers2.add(customer2);

        q1.setCustomers(customers1);
        q2.setCustomers(customers2);

        Set<Question> questions = new HashSet<>();
        questions.add(q1);
        questions.add(q2);

        root.setQuestions(questions);
        topicRepo.save(root);

        Slice<Customer> customers = customerRepo.getCustomersByTopicId(root.getId());
        assertNotNull(customers);
        assertEquals(customers.getContent().size(), 2);

        customerRepo.delete(customer1);
        customerRepo.delete(customer2);
        questionRepo.delete(q1);
        questionRepo.delete(q2);
        topicRepo.delete(root);
    }

    @Test
    public void test_getSortedSetOfCustomersByName() {
        Customer c1 = new Customer();
        c1.setName("fake customer 1");
        customerRepo.save(c1);
        Customer c2 = new Customer();
        c2.setName("fake customer 2");
        customerRepo.save(c2);

        Slice<Customer> customers = customerRepo.getSortedSetOfCustomersByName("fake customer.*");
        assertNotNull(customers);
        assertEquals(customers.getContent().size(), 2);
        customerRepo.delete(c1);
        customerRepo.delete(c2);
    }

    @Test
    public void test_getQuestionsInTopicByCustomer() {
        Topic root = new Topic();
        root.setTitle("root");

        Question q1 = new Question();
        q1.setQuestion("q1");
        Question q2 = new Question();
        q2.setQuestion("q2");

        Customer customer = new Customer();
        customer.setName("customer");

        Set<Customer> customers = new HashSet<>();
        customers.add(customer);

        q1.setCustomers(customers);
        q2.setCustomers(customers);

        Set<Question> questions = new HashSet<>();
        questions.add(q1);
        questions.add(q2);

        root.setQuestions(questions);
        topicRepo.save(root);

        long questionCount = customerRepo.getQuestionsInTopicByCustomer(customer.getName(), root.getId());
        assertEquals(questionCount, 2);

        customerRepo.delete(customer);
        questionRepo.delete(q1);
        questionRepo.delete(q2);
        topicRepo.delete(root);
    }

    @Test
    public void test_getAllCustomers() {
        Customer fake1 = new Customer();
        fake1.setName("fake customer 1");
        Customer fake2 = new Customer();
        fake2.setName("fake customer 2");
        Customer fake3 = new Customer();
        fake3.setName("fake customer 3");
        customerRepo.save(fake1);
        customerRepo.save(fake2);
        customerRepo.save(fake3);

        Result<Customer> customers = customerRepo.findAll();
        assertNotNull(customers);
        Set<Customer> set = Converter.convertToHashSet(customers);
        assertNotNull(set);

        customerRepo.delete(fake1.getId());
        customerRepo.delete(fake2.getId());
        customerRepo.delete(fake3.getId());
    }
}
