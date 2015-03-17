package com.epam.eighty.web.api;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import com.epam.eighty.domain.Question;
import com.epam.eighty.service.QuestionService;

/**
 * @author Yauheni_Razhkou
 *
 */
public class QuestionControllerTest {

    private static final long TEST_LONG = 1L;
    private static final String TEST_STRING = "fake";

    @InjectMocks
    private QuestionController questionController;

    @Mock
    private QuestionService questionService;

    @Mock
    private Pageable pageable;

    @Mock
    private Question question;

    @Mock
    private HttpServletResponse response;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_createQuestion_successful_case() {
        when(question.getId()).thenReturn(TEST_LONG);
        questionController.createQuestion(question, TEST_LONG, response);
        verify(questionService, Mockito.times(1)).addQuestion(question, TEST_LONG);
        verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    public void test_deleteQuestion_successful_case() {
        questionController.deleteQuestion(TEST_LONG, response);
        verify(questionService, Mockito.times(1)).deleteQuestion(TEST_LONG);
        verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void test_getAllQuestionsForTopic() {
        when(questionService.getQuestionsByTopicId(TEST_LONG, pageable)).thenReturn(Collections.<Question> emptyList());
        questionController.getAllQuestionsForTopic(TEST_LONG, pageable);
        verify(questionService, Mockito.times(1)).getQuestionsByTopicId(1l, pageable);
    }

    @Test
    public void test_getAllQuestionsForTopicWithTag() {
        when(questionService.getQuestionsByTopicIdAndTag(TEST_LONG, TEST_STRING)).thenReturn(Collections.<Question> emptyList());
        questionController.getAllQuestionsForTopicWithTag(TEST_LONG, TEST_STRING);
        verify(questionService, Mockito.times(1)).getQuestionsByTopicIdAndTag(1L, "fake");
    }

    @Test
    public void test_getAllQuestions() {
        questionController.getAllQuestions();
        verify(questionService, Mockito.times(1)).getAllQuestions();
    }

    @Test
    public void test_getAllQuestionsByTag() {
        when(questionService.getQuestionsByTag(TEST_STRING)).thenReturn(Collections.<Question> emptyList());
        questionController.getAllQuestionsByTag(TEST_STRING);
        verify(questionService, Mockito.times(1)).getQuestionsByTag(TEST_STRING);
    }

    @Test
    public void test_getAllQuestionsByCustomer() {
        when(questionService.getQuestionsByCustomerName("fake.fake")).thenReturn(Collections.<Question> emptyList());
        questionController.getAllQuestionsByCustomer("fake|fake");
        verify(questionService, Mockito.times(1)).getQuestionsByCustomerName("fake.fake");
    }

    @Test
    public void test_getQuestion_successful_case_with_existing_questuion() {
        when(questionService.getQuestionById(TEST_LONG)).thenReturn(question);
        assertTrue(questionController.getQuestion(TEST_LONG).equals(question));
    }

    @Test
    public void test_updateQuestion_successful_case() {
        questionController.updateQuestion(question, response);
        verify(questionService, Mockito.times(1)).updateQuestion(question);
        verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_OK);
    }

}
