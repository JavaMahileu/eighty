package com.epam.eighty.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.stereotype.Repository;

import com.epam.eighty.domain.Question;

/**
 * @author Aliaksandr_Padalka
 */
@Repository("questionRepo")
public interface QuestionRepository extends BaseRepository<Question, Long> {

    @Query(value = "MATCH (a:`Question`)<-[:`contains`*]-(b:`Topic`) WHERE ID(b) = {0} RETURN a", elementClass = Question.class)
    Slice<Question> getQuestions(Long topicId, Pageable pageable);

    @Query(value = "MATCH (t:`Topic`)-[:`contains`*]->(q:`Question`)-[:`has`*]->(tag:`Tag`) WHERE ID(t) = {0} AND tag.tag={1} RETURN DISTINCT q", elementClass = Question.class)
    Slice<Question> getQuestionsByTopicAndTag(Long topicId, String tag);

    @Query(value = "MATCH (a:`Question`)-[:`has`*]->(b) WHERE b.tag = {0} RETURN a", elementClass = Question.class)
    Slice<Question> getQuestionsByTag(String tag);

    @Query(value = "MATCH (a:`Question`)-[:`has`*]->(b) WHERE b.name = {0} RETURN a", elementClass = Question.class)
    Slice<Question> getQuestionsByCustomer(String customer);

}
