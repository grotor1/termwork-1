package com.grotor.termwork1sem.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AbstractMapper<T> {
    List<T> map(ResultSet rs) throws SQLException;
}
