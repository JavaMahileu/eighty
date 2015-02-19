package com.epam.eighty.domain;

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
        int result = (getId() != null ? getId().hashCode() : super.hashCode());
        final int i = 31;
        result = i * result + (tag != null ? tag.hashCode() : 0);
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
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!getId().equals(other.getId())) {
            return false;
        }
        if (tag == null) {
            if (other.tag != null) {
                return false;
            }
        } else if (!tag.equals(other.tag)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(final Tag o) {
        if (o == null) {
            return 1;
        }
        return this.tag.compareTo(o.tag);
    }

}
