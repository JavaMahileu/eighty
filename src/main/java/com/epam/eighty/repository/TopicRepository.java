package com.epam.eighty.repository;

import org.springframework.stereotype.Repository;

import com.epam.eighty.domain.Topic;

/**
 * @author Aliaksandr_Padalka
 */
@Repository("topicRepo")
public interface TopicRepository extends BaseRepository<Topic, Long> {

}
