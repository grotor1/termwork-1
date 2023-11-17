package com.grotor.termwork1sem.dao;

import com.grotor.termwork1sem.entities.Account;
import com.grotor.termwork1sem.entities.Anime;
import com.grotor.termwork1sem.entities.Studio;
import com.grotor.termwork1sem.helpers.DefaultValues;
import com.grotor.termwork1sem.mappers.AccountMapper;
import com.grotor.termwork1sem.mappers.AnimeMapper;
import com.grotor.termwork1sem.mappers.StudioMapper;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StudioDAO implements AbstractDAO<Studio> {
    private Connection CONN;

    //language=sql
    private final String SAVE = "insert into studio(id, name, description, photo, foundation_date) values (?, ?, ?, ?, ?, ?)";
    //language=sql
    private final String UPDATE = "update studio set name = ?, description = ?, photo = ?, foundation_date = ? where id = ?";
    //language=sql
    private final String GET_ALL = "select * from studio";
    //language=sql
    private final String GET = "select * from studio where id = ?";
    //language=sql
    private final String DELETE = "delete from studio where id = ?";
    //language=sql
    private final String GET_ANIME = "select distinct anime.* from anime, studio where anime.studio_id = ?";


    public StudioDAO(Connection conn) {
        this.CONN = conn;
    }

    @Override
    public boolean save(Studio entity) {
        try {
            PreparedStatement ps = CONN.prepareStatement(SAVE);
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, entity.getName());
            ps.setString(3, Optional.ofNullable(entity.getDescription()).orElse(DefaultValues.DEFAULT_STRING));
            ps.setString(4, Optional.ofNullable(entity.getPhoto()).orElse(DefaultValues.DEFAULT_USER_PHOTO));
            ps.setDate(5, Optional.ofNullable(entity.getFoundationDate()).orElse(DefaultValues.DEFAULT_DATE));
            return ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(UUID id, Studio entity) {
        try {
            PreparedStatement ps = CONN.prepareStatement(UPDATE);
            ps.setString(1, entity.getName());
            ps.setString(2, Optional.ofNullable(entity.getDescription()).orElse(DefaultValues.DEFAULT_STRING));
            ps.setString(3, Optional.ofNullable(entity.getPhoto()).orElse(DefaultValues.DEFAULT_USER_PHOTO));
            ps.setObject(4, id);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Studio> getAll() {
        try {
            PreparedStatement ps = CONN.prepareStatement(GET_ALL);
            return new StudioMapper().map(ps.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Studio> get(UUID id) {
        try {
            PreparedStatement ps = CONN.prepareStatement(GET);
            ps.setObject(1, id);
            Studio studio = new StudioMapper().map(ps.executeQuery()).get(0);
            if (studio == null) {
                return Optional.empty();
            } else {
                PreparedStatement aps = CONN.prepareStatement(GET_ANIME);
                aps.setObject(1, id);
                ResultSet crs = aps.executeQuery();
                List<Anime> cls = new AnimeMapper(CONN).map(crs);

                studio.setAnime(cls);

                return Optional.of(studio);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(UUID id) {
        try {
            PreparedStatement ps = CONN.prepareStatement(DELETE);
            ps.setObject(1, id);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
