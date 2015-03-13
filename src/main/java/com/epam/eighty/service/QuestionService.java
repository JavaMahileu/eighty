package com.epam.eighty.service;

import com.epam.eighty.domain.Question;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Aliaksandr_Padalka
 */
public interface QuestionService {

    Optional<Question> getQuestionById(Long id);
    Set<Question> getAllQuestions();
    void addQuestion(Question question, Long topicId);
    void updateQuestion(Question question);
    void deleteQuestion(Long id);
    List<Question> getQuestionsPage(Long topicId, Pageable pageable);
    List<Question> getQuestionsByTopicAndTag(Long topicId, String tag);
    List<Question> getQuestionsByTag(String tag);

    /**
     * Retrieves a list of {@link com.epam.eighty.domain.Question} based on customer's {@link com.epam.eighty.domain.Customer} name.
     *
     * @param customer a name of customer
     * @return a list of all customer's questions
     */
    List<Question> getQuestionsByCustomer(String customer);
}
