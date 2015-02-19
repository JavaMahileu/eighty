package com.epam.eighty.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
import com.epam.eighty.domain.Tag;
import com.epam.eighty.domain.Topic;
import com.epam.eighty.resources.TestNeo4jConfig;
import com.epam.eighty.utility.Converter;

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

    @Test
    public void test_addQuestion() {
        Question fake = new Question();
        fake.setQuestion("fake question");
        questionRepo.save(fake);

        Question q = questionRepo.findOne(fake.getId());

        assertNotNull(q);
        assertEquals(q, fake);

        questionRepo.delete(q);
    }

    @Test
    public void test_updateQuestion() {
        Question fake = new Question();
        fake.setQuestion("fake question");
        questionRepo.save(fake);

        fake.setQuestion("new fake question");
        questionRepo.save(fake);

        Question q = questionRepo.findOne(fake.getId());

        assertNotNull(q);
        assertEquals(q, fake);

        questionRepo.delete(q);
    }

    @Test
    public void test_deleteQuestion() {
        Question fake = new Question();
        fake.setQuestion("fake question");
        questionRepo.save(fake);

        Long id = fake.getId();

        questionRepo.delete(id);

        Question q = questionRepo.findBySchemaPropertyValue("question",
                fake.getQuestion());

        assertNull(q);
    }

    @Test
    public void test_getQuestionById() {
        Question fake1 = new Question();
        fake1.setQuestion("fake question 1");
        Question fake2 = new Question();
        fake2.setQuestion("fake question 2");
        Question fake3 = new Question();
        fake3.setQuestion("fake question 3");
        questionRepo.save(fake1);
        questionRepo.save(fake2);
        questionRepo.save(fake3);

        Question question;

        question = questionRepo.findOne(fake1.getId());
        assertNotNull(question);
        assertEquals(question, fake1);
        question = questionRepo.findOne(fake2.getId());
        assertNotNull(question);
        assertEquals(question, fake2);
        question = questionRepo.findOne(fake3.getId());
        assertNotNull(question);
        assertEquals(question, fake3);

        questionRepo.delete(fake1.getId());
        questionRepo.delete(fake2.getId());
        questionRepo.delete(fake3.getId());
    }

    @Test
    public void test_getAllQuestions() {
        Question fake1 = new Question();
        fake1.setQuestion("fake question 1");
        Question fake2 = new Question();
        fake2.setQuestion("fake question 2");
        Question fake3 = new Question();
        fake3.setQuestion("fake question 3");
        questionRepo.save(fake1);
        questionRepo.save(fake2);
        questionRepo.save(fake3);

        Result<Question> questions = questionRepo.findAll();
        assertNotNull(questions);
        Set<Question> set = Converter.convertToHashSet(questions);
        assertNotNull(set);

        questionRepo.delete(fake1.getId());
        questionRepo.delete(fake2.getId());
        questionRepo.delete(fake3.getId());
    }

    @Test
    public void test_getQuestions() {
        Topic root = new Topic();
        root.setTitle("root");
        Question q1 = new Question();
        q1.setQuestion("q1");
        Question q2 = new Question();
        q2.setQuestion("q2");
        Set<Question> set = new HashSet<>();
        set.add(q1);
        set.add(q2);
        root.setQuestions(set);
        topicRepo.save(root);

        Slice<Question> slice = questionRepo.getQuestions(root.getId(), null);
        assertNotNull(slice);

        questionRepo.delete(q1.getId());
        questionRepo.delete(q2.getId());
        topicRepo.delete(root.getId());
    }

    @Test
    public void test_getQuestionsByTopicAndTag() {
        Topic root = new Topic();
        root.setTitle("root");
        Question q1 = new Question();
        q1.setQuestion("q1");
        Question q2 = new Question();
        q2.setQuestion("q2");
        Tag tag = new Tag();
        tag.setTag("test");
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);
        q1.setTags(tags);
        q2.setTags(tags);
        Set<Question> set = new HashSet<>();
        set.add(q1);
        set.add(q2);
        root.setQuestions(set);
        topicRepo.save(root);

        Slice<Question> slice = questionRepo.getQuestionsByTopicAndTag(root.getId(), tag.getTag());
        assertNotNull(slice);
        assertEquals(slice.getContent().size(), 2);

        topicRepo.delete(tag.getId());
        questionRepo.delete(q1.getId());
        questionRepo.delete(q2.getId());
        topicRepo.delete(root.getId());
    }

    @Test
    public void test_getQuestionsByTag() {
        Question q1 = new Question();
        q1.setQuestion("q1");
        Question q2 = new Question();
        q2.setQuestion("q2");
        Tag tag = new Tag();
        tag.setTag("test");
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);
        q1.setTags(tags);
        q2.setTags(tags);
        questionRepo.save(q1);
        questionRepo.save(q2);

        Slice<Question> slice = questionRepo.getQuestionsByTag(tag.getTag());
        assertNotNull(slice);
        assertEquals(slice.getContent().size(), 2);

        topicRepo.delete(tag.getId());
        questionRepo.delete(q1.getId());
        questionRepo.delete(q2.getId());
    }

    @Test
    public void test_getQuestionsByCustomer() {
        Question q1 = new Question();
        q1.setQuestion("q1");
        Question q2 = new Question();
        q2.setQuestion("q2");
        Customer customer = new Customer();
        customer.setName("fake customer");
        Set<Customer> customers = new HashSet<>();
        customers.add(customer);
        q1.setCustomers(customers);
        q2.setCustomers(customers);
        questionRepo.save(q1);
        questionRepo.save(q2);

        Slice<Question> slice = questionRepo.getQuestionsByCustomer(customer.getName());
        assertNotNull(slice);
        assertEquals(slice.getContent().size(), 2);

        topicRepo.delete(customer.getId());
        questionRepo.delete(q1.getId());
        questionRepo.delete(q2.getId());
    }
}
