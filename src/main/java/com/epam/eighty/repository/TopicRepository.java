package com.epam.eighty.repository;

import com.epam.eighty.domain.Topic;
import org.springframework.data.domain.Slice;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Aliaksandr_Padalka
 */
@Repository("topicRepo")
public interface TopicRepository extends GraphRepository<Topic> {
    @Query(value = "MATCH (root:`Topic`)-[:`contains`*]->(topic:`Topic`) WHERE ID(topic) = {0} RETURN root", elementClass = Topic.class)
    Slice<Topic> getRootTopicsForTopic(Long id);
}
