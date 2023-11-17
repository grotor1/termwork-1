package com.grotor.termwork1sem.mappers;

import com.grotor.termwork1sem.entities.Studio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudioMapper implements AbstractMapper<Studio> {
    private Connection conn;

    @Override
    public List<Studio> map(ResultSet rs) throws SQLException {
        List<Studio> ls = new ArrayList<>();
        while (rs.next()) {
            Studio studio = new Studio();
            studio.setId(rs.getObject(1, UUID.class));
            studio.setName(rs.getString(2));
            studio.setDescription(rs.getString(3));
            studio.setPhoto(rs.getString(4));
            studio.setFoundationDate(rs.getDate(5));
            ls.add(studio);
        }
        return ls;
    }

    public StudioMapper() {

    }
}
