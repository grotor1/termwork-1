package com.grotor.termwork1sem.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AbstractDAO<T> {
    boolean save(T entity) throws SQLException;

    boolean update(UUID id, T entity);

    List<T> getAll();

    Optional<T> get(UUID id);

    boolean delete(UUID id);
}
