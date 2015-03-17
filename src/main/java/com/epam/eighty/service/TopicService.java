package com.epam.eighty.service;

import java.util.Optional;
import java.util.Set;

import com.epam.eighty.domain.Topic;

/**
 * @author Aliaksandr_Padalka
 */
public interface TopicService {

    Optional<Topic> getRoot();
    Optional<Topic> getTopicById(Long id);
    Set<Topic> getAllTopics();
    void updateTopic(Topic topic);
    void deleteTopic(Long id);
    Optional<Topic> getFullTopicById(Long id);
    Topic createTopic(Topic topic, Long id);
}
