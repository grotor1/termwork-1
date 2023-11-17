package com.grotor.termwork1sem.mappers;

import com.grotor.termwork1sem.dao.AccountDAO;
import com.grotor.termwork1sem.dao.AnimeDAO;
import com.grotor.termwork1sem.dao.CommentDAO;
import com.grotor.termwork1sem.entities.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PostMapper implements AbstractMapper<Post>{
    private final Connection CONN;

    public PostMapper(Connection conn) {
        CONN = conn;
    }

    @Override
    public List<Post> map(ResultSet rs) throws SQLException {
        List<Post> ls = new ArrayList<>();
        while (rs.next()) {
            Post post = new Post();
            post.setId(rs.getObject(1, UUID.class));
            post.setAuthor(new AccountDAO(CONN).get(rs.getObject(2, UUID.class)).orElse(new Account()));
            post.setPhoto(rs.getString(3));
            post.setText(rs.getString(4));
            post.setTime(rs.getDate(5));
            post.setTitle(rs.getString(6));
            post.setAnime(new AnimeDAO(CONN).get(rs.getObject(7, UUID.class)).orElse(new Anime()));
            ls.add(post);
        }
        return ls;
    }
}
