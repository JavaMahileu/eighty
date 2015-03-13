package com.epam.eighty.service;

import com.epam.eighty.domain.Customer;
import com.epam.eighty.domain.Question;
import com.epam.eighty.domain.Tag;
import com.epam.eighty.domain.Topic;
import com.epam.eighty.repository.QuestionRepository;
import com.epam.eighty.repository.TopicRepository;
import com.epam.eighty.service.impl.QuestionServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;
import org.springframework.data.neo4j.conversion.Result;

/**
 * @author Aliaksandr_Padalka
 */
@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceTest {

    private Question fake;
    private Topic root;
    private Tag tag;
    private Customer customer;

    private Set<Question> fakes;
    private Result<Question> results;
    List<Question> list;
    private Slice<Question> slice;

    @Mock
    private QuestionRepository questionRepo;
    @Mock
    private TopicRepository topicRepo;
    @InjectMocks
    private QuestionServiceImpl questionService;

    @Before
    public void setUp() {
        root = new Topic();
        root.setId(0L);

        tag = new Tag();
        tag.setId(100L);
        tag.setTag("tag");

        customer = new Customer();
        customer.setId(200L);
        customer.setName("customer");

        fake = new Question();
        fake.setId(1L);
        fake.setQuestion("fake question");

        Question fake0 = new Question();
        fake0.setId(10L);
        fake0.setQuestion("fake question 0");

        Question fake1 = new Question();
        fake1.setId(11L);
        fake1.setQuestion("fake question 1");

        Question fake2 = new Question();
        fake2.setId(12L);
        fake2.setQuestion("fake question 2");

        fakes = new HashSet<>();

        fakes.add(fake0);
        fakes.add(fake1);
        fakes.add(fake2);

        list = new ArrayList<>();
        list.add(fake0);
        list.add(fake1);
        list.add(fake2);

        results = new QueryResultBuilder<>(fakes);
        slice = new SliceImpl<>(list);
    }

    @Test
    public void test_getAllQuestions() {
        when(questionRepo.findAll()).thenReturn(results);

        Set<Question> set = questionService.getAllQuestions();

        assertNotNull(set);
        assertEquals(set, fakes);
    }

    @Test
    public void test_addQuestion() {
        when(topicRepo.findOne(root.getId())).thenReturn(root);
        questionService.addQuestion(fake, root.getId());
        verify(questionRepo).save(fake);
    }

    @Test
    public void test_updateQuestion() {
        questionService.updateQuestion(fake);
        verify(questionRepo).save(fake);
    }

    @Test
    public void test_deleteQuestion() {
        questionService.deleteQuestion(fake.getId());
        verify(questionRepo).delete(fake.getId());
    }

    @Test
    public void test_getQuestionById() {
        when(questionRepo.findOne(fake.getId())).thenReturn(fake);

        Question question = questionService.getQuestionById(1L).get();

        assertNotNull(question);
        assertEquals(question, fake);
    }

    @Test
    public void test_getQuestionsPage() {
        when(questionRepo.getQuestions(root.getId(), null)).thenReturn(slice);

        List<Question> questions = questionService.getQuestionsPage(root.getId(), null);

        assertNotNull(questions);
        assertEquals(questions, list);
    }


    @Test
    public void test_getQuestionsByTopicAndTag() {
        when(questionRepo.getQuestionsByTopicAndTag(root.getId(), tag.getTag())).thenReturn(slice);

        List<Question> questions = questionService.getQuestionsByTopicAndTag(root.getId(), tag.getTag());

        assertNotNull(questions);
        assertEquals(questions, list);
    }

    @Test
    public void test_getQuestionsByTag() {
        when(questionRepo.getQuestionsByTag(tag.getTag())).thenReturn(slice);

        List<Question> questions = questionService.getQuestionsByTag(tag.getTag());

        assertNotNull(questions);
        assertEquals(questions, list);
    }

    @Test
    public void test_getQuestionsByCustomer() {
        when(questionRepo.getQuestionsByCustomer(customer.getName())).thenReturn(slice);

        List<Question> questions = questionService.getQuestionsByCustomer(customer.getName());

        assertNotNull(questions);
        assertEquals(questions, list);
    }

}
