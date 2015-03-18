package com.epam.eighty.service;

import java.util.List;
import java.util.Optional;

import com.epam.eighty.domain.Topic;
import com.epam.eighty.exception.TopicNotFoundException;

/**
 * @author Aliaksandr_Padalka
 */
public interface TopicService {

    /**
     * Returns root {@link com.epam.eighty.domain.Topic Topic} entity wrapped in {@link java.util.Optional Optional}.
     * 
     * @return root topic
     */
    Optional<Topic> getRoot();
    
    /**
     * Returns {@link com.epam.eighty.domain.Topic Topic} entity with the given {@code id}.
     * 
     * @param id topic id
     * @return topic
     * @throws TopicNotFoundException when topic with the given {@code id} not found
     */
    Topic getTopicById(Long id);
    
    /**
     * Returns a list of all {@link com.epam.eighty.domain.Topic Topic} entities from the datastore.
     * 
     * @return the result list
     */
    List<Topic> getAllTopics();
    
    /**
     * Updates the given {@code topic} in the datastore.
     * 
     * @param topic topic to update
     */
    void updateTopic(Topic topic);
    
    /**
     * Deletes topic with the given {@code id} in the datastore.
     * 
     * @param id topic id
     */
    void deleteTopic(Long id);
    
    /**
     * Adds the given {@code topic} to topic with the given {@code id} and save it.
     * 
     * @param topic to add
     * @param id topic id to add to
     * @return added topic
     * @throws TopicNotFoundException when topic with the given {@code id} not found
     */
    Topic createTopic(Topic topic, Long id);
}
