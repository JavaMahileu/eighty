package com.epam.eighty.service;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;
import org.springframework.data.neo4j.conversion.Result;

import com.epam.eighty.domain.Customer;
import com.epam.eighty.domain.Question;
import com.epam.eighty.domain.Tag;
import com.epam.eighty.domain.Topic;
import com.epam.eighty.exception.TopicNotFoundException;
import com.epam.eighty.repository.QuestionRepository;
import com.epam.eighty.repository.TopicRepository;
import com.epam.eighty.service.impl.QuestionServiceImpl;

/**
 * @author Aliaksandr_Padalka
 */
@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceTest {
    
    private static final long TOPIC_ID = 5L;
    private static final String TAG_NAME = "tag name";

    private Question question;
    private Topic root;

    private Result<Question> results;
    private List<Question> questions;
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

        Tag tag = new Tag();
        tag.setId(100L);
        tag.setTag("tag");

        Customer customer = new Customer();
        customer.setId(200L);
        customer.setName("customer");

        question = new Question();
        question.setId(1L);
        question.setQuestion("fake question");

        Question question0 = new Question();
        question0.setId(10L);
        question0.setQuestion("fake question 0");

        Question question1 = new Question();
        question1.setId(11L);
        question1.setQuestion("fake question 1");

        Question question2 = new Question();
        question2.setId(12L);
        question2.setQuestion("fake question 2");

        questions = new ArrayList<>();
        questions.add(question0);
        questions.add(question1);
        questions.add(question2);

        results = new QueryResultBuilder<>(questions);
        slice = new SliceImpl<>(questions);
    }

    @Test
    public void test_getAllQuestions() {
        when(questionRepo.findAll()).thenReturn(results);

        List<Question> actualQuestions = questionService.getAllQuestions();

        assertThat(actualQuestions, contains(questions.toArray()));
    }

    @Test
    public void test_addQuestion() {
        when(topicRepo.findOne(root.getId())).thenReturn(Optional.of(root));
        
        questionService.addQuestion(question, root.getId());

        verify(questionRepo).save(question);
        verify(topicRepo).save(root);
        assertThat(root.getQuestions(), contains(question));
    }

    @Test(expected = TopicNotFoundException.class)
    public void test_addQuestionNegative() {
        when(topicRepo.findOne(root.getId())).thenReturn(Optional.empty());
        
        questionService.addQuestion(question, root.getId());
    }

    @Test
    public void test_updateQuestion() {
        questionService.updateQuestion(question);
        verify(questionRepo).save(question);
    }

    @Test
    public void test_deleteQuestion() {
        questionService.deleteQuestion(question.getId());
        
        verify(questionRepo).delete(question.getId());
    }

    @Test
    public void test_getQuestionsByTopicId() {
        when(questionRepo.getQuestionsByTopicId(TOPIC_ID, null)).thenReturn(slice);

        List<Question> actualQuestions = questionService.getQuestionsByTopicId(TOPIC_ID, null);

        assertThat(actualQuestions, contains(questions.toArray()));
    }

    @Test
    public void test_getQuestionsByTopicIdAndTag() {
        when(questionRepo.getQuestionsByTopicIdAndTag(TOPIC_ID, TAG_NAME)).thenReturn(questions);

        List<Question> actualQuestions = questionService.getQuestionsByTopicIdAndTag(TOPIC_ID, TAG_NAME);

        assertThat(actualQuestions, contains(questions.toArray()));
    }

    @Test
    public void test_getQuestionsByTag() {
        when(questionRepo.getQuestionsByTag(TAG_NAME)).thenReturn(questions);

        List<Question> actualQuestions = questionService.getQuestionsByTag(TAG_NAME);

        assertThat(actualQuestions, contains(questions.toArray()));
    }

    @Test
    public void test_getQuestionsByCustomer() {
        when(questionRepo.getQuestionsByCustomerName(TAG_NAME)).thenReturn(questions);

        List<Question> actualQuestions = questionService.getQuestionsByCustomerName(TAG_NAME);

        assertThat(actualQuestions, contains(questions.toArray()));
    }

}
