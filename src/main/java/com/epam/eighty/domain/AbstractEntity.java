package com.epam.eighty.domain;

import org.springframework.data.neo4j.annotation.GraphId;

/**
 * @author Aliaksandr_Padalka
 */
public abstract class AbstractEntity {

    @GraphId
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
