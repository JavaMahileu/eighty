package com.epam.eighty.service.impl;

import com.epam.eighty.domain.Question;
import com.epam.eighty.domain.Topic;
import com.epam.eighty.exception.TopicNotFoundException;
import com.epam.eighty.repository.QuestionRepository;
import com.epam.eighty.repository.TopicRepository;
import com.epam.eighty.service.QuestionService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Aliaksandr_Padalka
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepo;
    @Autowired
    private TopicRepository topicRepo;

    @Autowired
    private Neo4jOperations template;

    @Override
    public Optional<Question> getQuestionById(final Long id) {
        return questionRepo.findOne(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Question> getAllQuestions() {
        return questionRepo.findAll().as(Set.class);
    }

    @Override
    public void addQuestion(final Question question, final Long id) {
        Topic topic = topicRepo.findOne(id).orElseThrow(() -> new TopicNotFoundException(id));
        questionRepo.save(question);
        topic.getQuestions().add(question);
        topicRepo.save(topic);
    }

    @Override
    public void updateQuestion(final Question question) {
        questionRepo.save(question);
    }

    @Override
    public void deleteQuestion(final Long id) {
        questionRepo.delete(id);
    }

    @Override
    public List<Question> getQuestionsPage(final Long topicId, final Pageable pageable) {
        return questionRepo.getQuestionsByTopicId(topicId, pageable).getContent();
    }

    @Override
    public List<Question> getQuestionsByTopicAndTag(final Long topicId, final String tag) {
        return questionRepo.getQuestionsByTopicIdAndTag(topicId, tag);
    }

    @Override
    public List<Question> getQuestionsByTag(final String tag) {
        return questionRepo.getQuestionsByTag(tag);
    }

    @Override
    public List<Question> getQuestionsByCustomer(final String customer) {
        return questionRepo.getQuestionsByCustomerName(customer);
    }

}
