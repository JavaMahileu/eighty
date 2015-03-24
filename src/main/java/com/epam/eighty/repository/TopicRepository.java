package com.epam.eighty.repository;

import org.springframework.data.domain.Slice;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.stereotype.Repository;

import com.epam.eighty.domain.Topic;

/**
 * @author Aliaksandr_Padalka
 */
@Repository("topicRepo")
public interface TopicRepository extends BaseRepository<Topic, Long> {

    @Query(value = "MATCH (root:`Topic`) WHERE ID(root) = {0} return root UNION ALL MATCH (root:`Topic`)-[:`contains`*]->(topic:`Topic`) WHERE ID(topic) = {0} RETURN root", elementClass = Topic.class)
    Slice<Topic> getRootTopicsForTopic(Long id);
}
