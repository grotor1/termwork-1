package com.grotor.termwork1sem.mappers;

import com.grotor.termwork1sem.entities.Character;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CharacterMapper implements AbstractMapper<Character> {

    @Override
    public List<Character> map(ResultSet rs) throws SQLException {
        List<Character> ls = new ArrayList<>();
        while (rs.next()) {
            Character character = new Character();
            character.setId(rs.getObject(1, UUID.class));
            character.setName(rs.getString(2));
            character.setDescription(rs.getString(3));
            character.setPhoto(rs.getString(4));
            ls.add(character);
        }
        return ls;
    }
}
