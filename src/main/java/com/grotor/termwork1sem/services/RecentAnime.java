package com.grotor.termwork1sem.services;

import com.grotor.termwork1sem.dao.AnimeDAO;
import com.grotor.termwork1sem.entities.Anime;
import com.grotor.termwork1sem.helpers.DateUtils;

import java.sql.Connection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class RecentAnime {
    public final Connection CONN;

    public RecentAnime(Connection conn) {
        CONN = conn;
    }

    public List<HashMap<String, String>> getAnime() {
        AnimeDAO animeDAO = new AnimeDAO(CONN);

        return animeDAO.getAll().stream().sorted(Comparator.comparing(Anime::getStartDate).reversed()).map((item) -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("id", item.getId().toString());
            map.put("img", item.getPhoto());
            map.put("title", item.getTitle());
            map.put("date", DateUtils.getYear(item.getStartDate()));
            return map;
        }).toList().subList(0, 7);
    }
}
