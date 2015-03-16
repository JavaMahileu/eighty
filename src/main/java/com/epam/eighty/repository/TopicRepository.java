package com.epam.eighty.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.stereotype.Repository;

import com.epam.eighty.domain.Topic;

/**
 * @author Aliaksandr_Padalka
 */
@Repository("topicRepo")
public interface TopicRepository extends BaseRepository<Topic, Long> {
    
    @Query(value = "MATCH (root:`Topic`)-[:`contains`*]->(topic:`Topic`) WHERE ID(topic) = {0} RETURN root", elementClass = Topic.class)
    List<Topic> getRootTopicsForTopic(Long id);

}
