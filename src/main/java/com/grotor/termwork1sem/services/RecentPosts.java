package com.grotor.termwork1sem.services;

import com.grotor.termwork1sem.dao.PostDAO;
import com.grotor.termwork1sem.entities.Post;
import com.grotor.termwork1sem.helpers.DateUtils;

import java.sql.Connection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class RecentPosts {
    public final Connection CONN;

    public RecentPosts(Connection conn) {
        CONN = conn;
    }

    public List<HashMap<String, String>> getPosts() {
        PostDAO postDAO = new PostDAO(CONN);

        return postDAO.getAll().stream().sorted(Comparator.comparing(Post::getTime).reversed()).map((item) -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("id", item.getId().toString());
            map.put("img", item.getPhoto());
            map.put("title", item.getTitle());
            map.put("date", DateUtils.getFullDate(item.getTime()));
            return map;
        }).toList().subList(0, 6);
    }
}
