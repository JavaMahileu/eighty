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
import java.util.Optional;
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

    private Optional<Question> fake;
    private Optional<Topic> root;
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
        root = Optional.of(new Topic());
        root.get().setId(0L);

        tag = new Tag();
        tag.setId(100L);
        tag.setTag("tag");

        customer = new Customer();
        customer.setId(200L);
        customer.setName("customer");

        fake = Optional.of(new Question());
        fake.get().setId(1L);
        fake.get().setQuestion("fake question");

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

        List<Question> questions = questionService.getAllQuestions();

        assertNotNull(questions);
        assertEquals(questions, fakes);
    }

    @Test
    public void test_addQuestion() {
        when(topicRepo.findOne(root.get().getId())).thenReturn(root);
        questionService.addQuestion(fake.get(), root.get().getId());
        verify(questionRepo).save(fake.get());
    }

    @Test
    public void test_updateQuestion() {
        questionService.updateQuestion(fake.get());
        verify(questionRepo).save(fake.get());
    }

    @Test
    public void test_deleteQuestion() {
        questionService.deleteQuestion(fake.get().getId());
        verify(questionRepo).delete(fake.get().getId());
    }

    @Test
    public void test_getQuestionById() {
        when(questionRepo.findOne(fake.get().getId())).thenReturn(fake);

        Question question = questionService.getQuestionById(1L);

        assertNotNull(question);
        assertEquals(question, fake.get());
    }

    @Test
    public void test_getQuestionsPage() {
        when(questionRepo.getQuestionsByTopicId(root.get().getId(), null)).thenReturn(slice);

        List<Question> questions = questionService.getQuestionsByTopicId(root.get().getId(), null);

        assertNotNull(questions);
        assertEquals(questions, list);
    }


    @Test
    public void test_getQuestionsByTopicAndTag() {
        when(questionRepo.getQuestionsByTopicIdAndTag(root.get().getId(), tag.getTag())).thenReturn(list);

        List<Question> questions = questionService.getQuestionsByTopicIdAndTag(root.get().getId(), tag.getTag());

        assertNotNull(questions);
        assertEquals(questions, list);
    }

    @Test
    public void test_getQuestionsByTag() {
        when(questionRepo.getQuestionsByTag(tag.getTag())).thenReturn(list);

        List<Question> questions = questionService.getQuestionsByTag(tag.getTag());

        assertNotNull(questions);
        assertEquals(questions, list);
    }

    @Test
    public void test_getQuestionsByCustomer() {
        when(questionRepo.getQuestionsByCustomerName(customer.getName())).thenReturn(list);

        List<Question> questions = questionService.getQuestionsByCustomerName(customer.getName());

        assertNotNull(questions);
        assertEquals(questions, list);
    }

}
