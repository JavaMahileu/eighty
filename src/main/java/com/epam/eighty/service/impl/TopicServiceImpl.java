package com.epam.eighty.service.impl;

import com.epam.eighty.domain.Topic;
import com.epam.eighty.exception.TopicNotFoundException;
import com.epam.eighty.repository.TopicRepository;
import com.epam.eighty.service.TopicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Aliaksandr_Padalka
 */
@Service
@Transactional
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepo;

    @Autowired
    private Neo4jOperations template;

    @Override
    public Optional<Topic> getRoot() {
        final Optional<Topic> root = topicRepo.findBySchemaPropertyValue("title", "root");
        root.ifPresent(someRoot ->
            someRoot.getTopics().forEach(template::fetch)
        );
        return root;
    }

    @Override
    public Topic getTopicById(final Long id) {
        final Topic topic = topicRepo.findOne(id).orElseThrow(() -> new TopicNotFoundException(id));
        topic.getTopics().forEach(template::fetch);

        return topic;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Topic> getAllTopics() {
        return topicRepo.findAll().as(List.class);
    }

    @Override
    public void updateTopic(final Topic topic) {
        topicRepo.save(topic);
    }

    @Override
    public void deleteTopic(final Long id) {
        topicRepo.delete(id);
    }

    @Override
    public Topic createTopic(final Topic topic, final Long id) {
        final Topic parentTopic = topicRepo.findOne(id).orElseThrow(() -> new TopicNotFoundException(id));
        parentTopic.getTopics().add(topic);
        topicRepo.save(parentTopic);

        return topic;
    }

}
