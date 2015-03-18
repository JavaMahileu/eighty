package com.epam.eighty.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.epam.eighty.domain.Question;

/**
 * @author Aliaksandr_Padalka
 */
public interface QuestionService {

    Question getQuestionById(Long id);
    List<Question> getAllQuestions();
    void addQuestion(Question question, Long topicId);
    void updateQuestion(Question question);
    void deleteQuestion(Long id);
    List<Question> getQuestionsByTopicId(Long topicId, Pageable pageable);
    List<Question> getQuestionsByTopicIdAndTag(Long topicId, String tag);
    List<Question> getQuestionsByTag(String tag);

    /**
     * Retrieves a list of {@link com.epam.eighty.domain.Question} based on customer's {@link com.epam.eighty.domain.Customer} name.
     *
     * @param customer a name of customer
     * @return a list of all customer's questions
     */
    List<Question> getQuestionsByCustomerName(String customer);
}
