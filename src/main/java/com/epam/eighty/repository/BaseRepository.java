package com.epam.eighty.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {

    Optional<T> findOne(Long id);

    T save(T entity);

    Result<T> findAll();

    void delete(ID id);

    void delete(T entity);

    @Transactional
    Optional<T> findBySchemaPropertyValue(String property, Object value);
  }
