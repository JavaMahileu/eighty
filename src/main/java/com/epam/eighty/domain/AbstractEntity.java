package com.epam.eighty.domain;

import java.util.Optional;

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

    protected static <T> int getHashCodeOrDefaultValue(final T field, final int defaultValue) {
        return Optional.ofNullable(field).map(Object::hashCode).orElse(defaultValue);
    }
}
