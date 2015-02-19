package com.epam.eighty.repository;

import com.epam.eighty.domain.Tag;
import org.springframework.data.domain.Slice;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Aliaksandr_Padalka
 */
@Repository("tagRepo")
public interface TagRepository extends GraphRepository<Tag> {

    @Query(value = "MATCH (tag:`Tag`)<-[:`has`*]-(question:`Question`)<-[:`contains`*]-(topic) WHERE ID(topic) = {0} RETURN DISTINCT tag", elementClass = Tag.class)
    Slice<Tag> getTagsByTopicId(Long id);

    @Query(value = "MATCH (tag:`Tag`)<-[r:`has`*]-q WITH tag, count(r) AS connection RETURN tag ORDER BY connection DESC LIMIT {0}", elementClass = Tag.class)
    Slice<Tag> getTopNFromAllTags(Long limit);

    @Query(value = "MATCH (tag:`Tag`) WHERE tag.tag =~{0} RETURN tag", elementClass = Tag.class)
    Slice<Tag> getSortedSetOfTagsByName(String tagName);

    @Query(value = "MATCH (tag:`Tag`) WHERE NOT(tag<-[:has]-()) DELETE tag", elementClass = Tag.class)
    void removeTagsWithoutQuestion();

    /**
     * Retrieves a count of questions from customer in topic.
     *
     * @param tag a name of tag
     * @param topicId id of a topic
     * @return count of questions
     */
    @Query(value = "MATCH (tag:`Tag`)<-[r:`has`*]-(question:`Question`)<-[:`contains`*]-(topic:`Topic`) WHERE  tag.tag = {0} AND Id(topic) = {1} RETURN count(r)", elementClass = Long.class)
    Long getQuestionsInTopicByTag(String tag, Long topicId);
}
