package com.grotor.termwork1sem.services;

import com.grotor.termwork1sem.dao.AnimeDAO;
import com.grotor.termwork1sem.dao.PostDAO;
import com.grotor.termwork1sem.entities.Anime;
import com.grotor.termwork1sem.entities.Post;
import com.grotor.termwork1sem.helpers.DateUtils;

import java.sql.Connection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class PostsList {
    private final Connection CONN;
    private static final int ITEMS_ON_PAGE = 9;

    public PostsList(Connection conn) {
        CONN = conn;
    }

    public int getPageCount() {
        PostDAO postDAO = new PostDAO(CONN);

        return (int) Math.ceil((double) postDAO.getAll().size() / ITEMS_ON_PAGE);
    }

    public List<HashMap<String, String>> getPostPage(int page) {
        PostDAO postDAO = new PostDAO(CONN);

        List<HashMap<String, String>> list = postDAO.getAll().stream().sorted(Comparator.comparing(Post::getTime).reversed()).map((item) -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("id", item.getId().toString());
            map.put("img", item.getPhoto());
            map.put("title", item.getTitle());
            map.put("date", DateUtils.getFullDate(item.getTime()));
            return map;
        }).toList();

        return list.subList((page - 1) * ITEMS_ON_PAGE, Math.min(page * ITEMS_ON_PAGE, list.size()));
    }
}
