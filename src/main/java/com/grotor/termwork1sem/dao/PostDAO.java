package com.grotor.termwork1sem.dao;

import com.grotor.termwork1sem.entities.Anime;
import com.grotor.termwork1sem.entities.Character;
import com.grotor.termwork1sem.entities.Person;
import com.grotor.termwork1sem.entities.Post;
import com.grotor.termwork1sem.entities.Staff;
import com.grotor.termwork1sem.enums.StaffType;
import com.grotor.termwork1sem.helpers.DefaultValues;
import com.grotor.termwork1sem.mappers.AnimeMapper;
import com.grotor.termwork1sem.mappers.CharacterMapper;
import com.grotor.termwork1sem.mappers.PersonMapper;
import com.grotor.termwork1sem.mappers.PostMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PostDAO implements AbstractDAO<Post> {
    private final Connection CONN;
    //language=sql
    private final String SAVE = "insert into post(id, author_id, photo, text, anime_id, title) values (?, ?, ?, ?, ?, ?)";
    //language=sql
    private final String UPDATE = "update post set photo = ?, text = ?, title = ? where id = ?";
    //language=sql
    private final String GET_ALL = "select * from post";
    //language=sql
    private final String GET = "select * from post where id = ?";
    //language=sql
    private final String DELETE = "delete from post where id = ?";

    public PostDAO(Connection conn) {
        CONN = conn;
    }

    @Override
    public boolean save(Post entity) {
        try {
            PreparedStatement ps = CONN.prepareStatement(SAVE);
            ps.setObject(1, UUID.randomUUID());
            ps.setObject(2, entity.getAuthor().getId());
            ps.setString(3, Optional.ofNullable(entity.getPhoto()).orElse(DefaultValues.DEFAULT_ANIME_PHOTO));
            ps.setString(4, entity.getText());
            ps.setObject(5, entity.getAnime().getId());
            ps.setString(6, entity.getTitle());
            return ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(UUID id, Post entity) {
        try {
            PreparedStatement ps = CONN.prepareStatement(UPDATE);
            ps.setString(1, Optional.ofNullable(entity.getPhoto()).orElse(DefaultValues.DEFAULT_ANIME_PHOTO));
            ps.setString(2, entity.getText());
            ps.setString(3, entity.getTitle());
            ps.setObject(4, id);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> getAll() {
        try {
            PreparedStatement ps = CONN.prepareStatement(GET_ALL);
            return new PostMapper(CONN).map(ps.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Post> get(UUID id) {
        try {
            PreparedStatement ps = CONN.prepareStatement(GET);
            ps.setObject(1, id);
            Post post = new PostMapper(CONN).map(ps.executeQuery()).get(0);
            if (post == null) {
                return Optional.empty();
            } else {
                return Optional.of(post);
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
