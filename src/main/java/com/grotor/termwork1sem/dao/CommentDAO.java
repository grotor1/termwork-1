package com.grotor.termwork1sem.dao;

import com.grotor.termwork1sem.entities.Comment;
import com.grotor.termwork1sem.entities.Post;
import com.grotor.termwork1sem.helpers.DefaultValues;
import com.grotor.termwork1sem.mappers.CommentMapper;
import com.grotor.termwork1sem.mappers.PostMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CommentDAO implements AbstractDAO<Comment>{
    private final Connection CONN;
    //language=sql
    private final String SAVE = "insert into comment(id, author_id, text, entity_id) values (?, ?, ?, ?);";
    //language=sql
    private final String UPDATE = "update comment set text = ? where id = ?";
    //language=sql
    private final String GET_ALL = "select * from comment";
    //language=sql
    private final String GET = "select * from comment where id = ?";
    //language=sql
    private final String DELETE = "delete from comment where id = ?";
    //language=sql
    private final String GET_BY_ENTITY = "select * from comment where entity_id = ?";


    public CommentDAO(Connection conn) {
        CONN = conn;
    }

    @Override
    public boolean save(Comment entity) {
        try {
            PreparedStatement ps = CONN.prepareStatement(SAVE);
            ps.setObject(1, UUID.randomUUID());
            ps.setObject(2, entity.getAuthor().getId());
            ps.setString(3, entity.getText());
            ps.setObject(4, entity.getEntityId());
            return ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(UUID id, Comment entity) {
        try {
            PreparedStatement ps = CONN.prepareStatement(UPDATE);
            ps.setString(1, entity.getText());
            ps.setObject(2, id);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try {
            PreparedStatement ps = CONN.prepareStatement(GET_ALL);
            return new CommentMapper(CONN).map(ps.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Comment> getByEntity(UUID id) {
        try {
            PreparedStatement ps = CONN.prepareStatement(GET_BY_ENTITY);
            ps.setObject(1, id);
            return new CommentMapper(CONN).map(ps.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Comment> get(UUID id) {
        try {
            PreparedStatement ps = CONN.prepareStatement(GET);
            ps.setObject(1, id);
            Comment comment = new CommentMapper(CONN).map(ps.executeQuery()).get(0);
            if (comment == null) {
                return Optional.empty();
            } else {
                return Optional.of(comment);
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
