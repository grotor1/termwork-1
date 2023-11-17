package com.grotor.termwork1sem.mappers;

import com.grotor.termwork1sem.dao.AccountDAO;
import com.grotor.termwork1sem.dao.CommentDAO;
import com.grotor.termwork1sem.entities.Account;
import com.grotor.termwork1sem.entities.Comment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentMapper implements AbstractMapper<Comment>{
    private final Connection CONN;

    public CommentMapper(Connection conn) {
        CONN = conn;
    }

    @Override
    public List<Comment> map(ResultSet rs) throws SQLException {
        List<Comment> ls = new ArrayList<>();
        while (rs.next()) {
            Comment comment = new Comment();
            comment.setId(rs.getObject(1, UUID.class));
            comment.setAuthor(new AccountDAO(CONN).get(rs.getObject(2, UUID.class)).orElse(new Account()));
            comment.setText(rs.getString(3));
            comment.setTime(rs.getDate(4));
            comment.setEntityId(rs.getObject(5, UUID.class));
            ls.add(comment);
        }
        return ls;
    }
}
