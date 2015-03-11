package com.epam.eighty.domain;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author Aliaksandr_Padalka
 */
@ApiModel(value = "The classification for a topic", description = "Defines a specific classification for a topic")
@NodeEntity
public class Topic extends AbstractEntity {

    @ApiModelProperty(value = "title of topic", notes = "This value indexed", required = true, dataType = "string")
    @Indexed
    private String title;

    @ApiModelProperty(value = "tree of subtopics", dataType = "relationship between 2 topics")
    @RelatedTo(type = "contains", elementClass = Topic.class, direction = Direction.OUTGOING, enforceTargetType = true)
    private Set<Topic> topics;

    @ApiModelProperty(value = "tree of questions", dataType = "relationship between topic and questions")
    @RelatedTo(type = "contains", elementClass = Question.class, direction = Direction.OUTGOING, enforceTargetType = true)
    private Set<Question> questions;

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(final Set<Topic> topics) {
        this.topics = topics;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(final Set<Question> questions) {
        this.questions = questions;
    }

    public Topic() {
        topics = new HashSet<>();
        questions = new HashSet<>();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Topic)) {
            return false;
        }
        Topic other = (Topic) obj;
        if (!Objects.equals(getId(), other.getId())) {
            return false;
        }
        if (!Objects.equals(questions, other.questions)) {
            return false;
        }
        if (!Objects.equals(title, other.title)) {
            return false;
        }
        if (!Objects.equals(topics, other.topics)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("Topic:" + " ");
        Optional.ofNullable(getId()).ifPresent(id -> result.append("id=" + id.toString() + " "));
        result.append("title=" + title);

        return result.toString();
    }

    @Override
    public final int hashCode() {
        int result = getHashCodeOrDefaultValue(getId(), super.hashCode());
        result = 31 * result + getHashCodeOrDefaultValue(title, 0);
        result = 31 * result + getHashCodeOrDefaultValue(topics, 0);
        result = 31 * result + getHashCodeOrDefaultValue(questions, 0);
        return result;
    }
}
