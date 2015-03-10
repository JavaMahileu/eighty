package com.epam.eighty.service.impl;

import com.epam.eighty.domain.Topic;
import com.epam.eighty.repository.TopicRepository;
import com.epam.eighty.service.TopicService;
import com.epam.eighty.utility.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public Topic getRoot() {
        Topic root = topicRepo.findBySchemaPropertyValue("title", "root");
        Optional.ofNullable(root).ifPresent(someRoot ->
            someRoot.getTopics()
                .forEach(template::fetch)
        );
        return root;
    }

    @Override
    public Topic getFullTopicById(final Long id) {
        Topic topic = topicRepo.findOne(id);
        Optional.ofNullable(topic).ifPresent(t -> {
            t.getTopics()
                .forEach(template::fetch);
            t.getQuestions()
                .forEach(template::fetch);
        });
        return topic;
    }

    @Override
    public Topic getTopicById(final Long id) {
        Topic topic = topicRepo.findOne(id);
        Optional.ofNullable(topic).ifPresent(t ->
            topic.getTopics()
                .forEach(template::fetch)
        );
        return topic;
    }

    @Override
    public Set<Topic> getAllTopics() {
        Result<Topic> topics = topicRepo.findAll();
        return Converter.convertToHashSet(topics);
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
        Topic parentTopic = topicRepo.findOne(id);
        parentTopic.getTopics().add(topic);
        topicRepo.save(parentTopic);

        return topic;
    }

    @Override
    public List<Topic> getRootTopicsForTopic(final Long id) {
        return topicRepo.getRootTopicsForTopic(id).getContent();
    }
}
