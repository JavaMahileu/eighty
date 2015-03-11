package com.epam.eighty.domain;

import java.util.Optional;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.Query;

/**
 * @author Aliaksandr_Padalka
 */
@ApiModel(value = "The classification for a tag", description = "Defines a specific classification for a tag")
@NodeEntity
public class Tag extends AbstractEntity implements Comparable<Tag> {

    @ApiModelProperty(value = "tag", notes = "This value indexed", required = true, dataType = "string")
    @Indexed(unique = true)
    private String tag;

    @Query(value = "start tag=node({self}) MATCH (tag:`Tag`)<-[r:`has`*]-() WITH tag, count(r) AS connections RETURN connections")
    private Long count;

    private Long countInTopic;

    public String getTag() {
        return tag;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(final Long count) {
        this.count = count;
    }

    public Long getCountInTopic() {
        return countInTopic;
    }

    public void setCountInTopic(final Long countInTopic) {
        this.countInTopic = countInTopic;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("Tag:" + " ").append("id=" + getId().toString() + " ")
                .append("tag=" + tag);

        return result.toString();
    }

    @Override
    public final int hashCode() {
        int result = getHashCodeOrDefaultValue(getId(), super.hashCode());
        final int i = 31;
        result = i * result + getHashCodeOrDefaultValue(tag, 0);
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
        if (!(obj instanceof Tag)) {
            return false;
        }
        Tag other = (Tag) obj;
        if (!areFieldsEquals(getId(), other.getId())) {
            return false;
        }
        if (!areFieldsEquals(tag, other.tag)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(final Tag o) {
        return Optional.ofNullable(o).map(obj -> this.tag.compareTo(obj.tag)).orElse(1);
    }

}
