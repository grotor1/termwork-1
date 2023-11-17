package com.grotor.termwork1sem.services;

import com.grotor.termwork1sem.dao.CommentDAO;
import com.grotor.termwork1sem.entities.Comment;
import com.grotor.termwork1sem.helpers.DateUtils;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CommentsList {
    private Connection CONN;

    public CommentsList(Connection conn) {
        CONN = conn;
    }

    public void addComments(Map<String, Object> root, UUID uuid) {
        CommentDAO commentDAO = new CommentDAO(CONN);
        List<Comment> list = commentDAO.getByEntity(uuid);

        root.put("comments", list.stream().map((item) -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("img", item.getAuthor().getPhoto());
            hashMap.put("userId", item.getAuthor().getId().toString());
            hashMap.put("name", item.getAuthor().getName());
            hashMap.put("date", DateUtils.getFullDate(item.getTime()));
            hashMap.put("text", item.getText());
            return hashMap;
        }).toList());
    }
}
