package com.grotor.termwork1sem.mappers;

import com.grotor.termwork1sem.entities.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonMapper implements AbstractMapper<Person> {
    @Override
    public List<Person> map(ResultSet rs) throws SQLException {
        List<Person> ls = new ArrayList<>();
        while (rs.next()) {
            Person person = new Person();
            person.setId(rs.getObject(1, UUID.class));
            person.setName(rs.getString(2));
            person.setDescription(rs.getString(3));
            person.setPhoto(rs.getString(4));
            person.setBirthdate(rs.getDate(5));
            ls.add(person);
        }
        return ls;
    }
}
