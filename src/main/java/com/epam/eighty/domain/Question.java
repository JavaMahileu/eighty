package com.epam.eighty.domain;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Aliaksandr_Padalka
 */
@ApiModel(value = "The classification for a question", description = "Defines a specific classification for a question")
@NodeEntity
public class Question extends AbstractEntity {

    @ApiModelProperty(value = "question", notes = "This value indexed", required = true, dataType = "string")
    @Indexed
    private String question;

    @ApiModelProperty(value = "answer", required = false, dataType = "string")
    private String answer;

    @ApiModelProperty(value = "like", required = false, dataType = "int")
    private Integer like;

    @Fetch
    @RelatedTo(type = "has", elementClass = Tag.class, direction = Direction.OUTGOING, enforceTargetType = true)
    private Set<Tag> tags;

    @Fetch
    @RelatedTo(type = "has", elementClass = Customer.class, direction = Direction.OUTGOING, enforceTargetType = true)
    private Set<Customer> customers;

    public Question() {
        this.question = "";
        this.answer = "";
        this.like = 0;
        this.tags = new HashSet<Tag>();
        this.customers = new HashSet<Customer>();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Question: ");
        Optional.ofNullable(getId()).ifPresent(id -> result.append("id=" + id.toString() + " "));
        result.append("question=" + question + " ")
            .append("answer=" + answer + " ")
            .append("like=" + (Optional.ofNullable(like).orElse(0)));
        return result.toString();
    }

    @Override
    public final int hashCode() {
        int result = getHashCodeOrDefaultValue(getId(), super.hashCode());
        final int i = 31;
        result = i * result + getHashCodeOrDefaultValue(question, super.hashCode());
        result = i * result + getHashCodeOrDefaultValue(answer, super.hashCode());
        result = i * result + getHashCodeOrDefaultValue(like, super.hashCode());
        result = i * result + getHashCodeOrDefaultValue(tags, super.hashCode());
        result = i * result + getHashCodeOrDefaultValue(customers, super.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Question)) {
            return false;
        }
        Question other = (Question) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!getId().equals(other.getId())) {
            return false;
        }
        if (!Optional.ofNullable(answer).isPresent()) {
            if (Optional.ofNullable(other.answer).isPresent()) {
                return false;
            }
        } else if (!answer.equals(other.answer)) {
            return false;
        }
        if (!Optional.ofNullable(like).isPresent()) {
            if (Optional.ofNullable(other.like).isPresent()) {
                return false;
            }
        } else if (!like.equals(other.like)) {
            return false;
        }
        if (!Optional.ofNullable(question).isPresent()) {
            if (Optional.ofNullable(other.question).isPresent()) {
                return false;
            }
        } else if (!question.equals(other.question)) {
            return false;
        }
        if (!Optional.ofNullable(tags).isPresent()) {
            if (Optional.ofNullable(other.tags).isPresent()) {
                return false;
            }
        } else if (!tags.equals(other.tags)) {
            return false;
        }
        if (!Optional.ofNullable(customers).isPresent()) {
            if (Optional.ofNullable(customers).isPresent()) {
                return false;
            }
        } else if (!customers.equals(other.customers)) {
            return false;
        }
        return true;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question
     *            the question to set
     */
    public void setQuestion(final String question) {
        this.question = question;
    }

    /**
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer
     *            the answer to set
     */
    public void setAnswer(final String answer) {
        this.answer = answer;
    }

    /**
     * @return the like
     */
    public Integer getLike() {
        return like;
    }

    /**
     * @param like
     *            the like to set
     */
    public void setLike(final Integer like) {
        this.like = like;
    }

    /**
     * @return the tags
     */
    public Set<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags
     *            the tags to set
     */
    public void setTags(final Set<Tag> tags) {
        this.tags = tags;
    }

    /**
     * @return the customers
     */
    public Set<Customer> getCustomers() {
        return customers;
    }

    /**
     * @param customers
     *            the customer to set
     */
    public void setCustomers(final Set<Customer> customers) {
        this.customers = customers;
    }

}
