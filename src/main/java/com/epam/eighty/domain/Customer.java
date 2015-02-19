package com.epam.eighty.domain;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.Query;

/**
 * @author Raman_Tsimoshchanka
 */
@ApiModel(value = "The classification for a customer", description = "Defines a specific classification for a customer")
@NodeEntity
public class Customer extends AbstractEntity implements Comparable<Customer> {

    @ApiModelProperty(value = "name", notes = "This value indexed", required = true, dataType = "string")
    @Indexed(unique = true)
    private String name;

    @Query(value = "START customer=node({self}) MATCH (customer:`Customer`)<-[r:`has`*]-() WITH customer, count(r) AS connections RETURN connections")
    private Long count;

    private Long countInTopic;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
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
        return ("id=" + getId().toString() + " ") + "name=" + name;
    }

    @Override
    public final int hashCode() {
        int result = (getId() != null ? getId().hashCode() : super.hashCode());
        result = 31 * result + (name != null ? name.hashCode() : 0);
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
        if (!(obj instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!getId().equals(other.getId())) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(final Customer o) {
        if (o == null) {
            return 1;
        }
        return this.name.compareTo(o.name);
    }

}
