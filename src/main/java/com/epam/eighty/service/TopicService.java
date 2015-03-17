package com.epam.eighty.service;

import java.util.List;
import java.util.Optional;

import com.epam.eighty.domain.Topic;

/**
 * @author Aliaksandr_Padalka
 */
public interface TopicService {

    Optional<Topic> getRoot();
    Topic getTopicById(Long id);
    List<Topic> getAllTopics();
    void updateTopic(Topic topic);
    void deleteTopic(Long id);
    Topic createTopic(Topic topic, Long id);
}
