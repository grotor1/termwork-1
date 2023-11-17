package com.grotor.termwork1sem.services;

import com.grotor.termwork1sem.dao.AnimeDAO;
import com.grotor.termwork1sem.entities.Anime;
import com.grotor.termwork1sem.helpers.DateUtils;

import java.sql.Connection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class AnimeList {
    private final Connection CONN;
    private static final int ITEMS_ON_PAGE = 14;

    public AnimeList(Connection conn) {
        CONN = conn;
    }

    public int getPageCount() {
        AnimeDAO animeDAO = new AnimeDAO(CONN);

        return (int) Math.ceil((double) animeDAO.getAll().size() / ITEMS_ON_PAGE);
    }

    public int getPageCount(String q) {
        AnimeDAO animeDAO = new AnimeDAO(CONN);

        return Math.max((int) Math.ceil((double) animeDAO.getAll().stream().filter((item) -> item.getTitle().contains(q)).toList().size() / ITEMS_ON_PAGE), 1);
    }

    private HashMap<String, String> map(Anime item) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", item.getId().toString());
        map.put("img", item.getPhoto());
        map.put("title", item.getTitle());
        map.put("type", item.getAnimeType().text);
        map.put("status", item.getStatus().text);
        map.put("date", DateUtils.getYear(item.getStartDate()));
        return map;
    }

    public List<HashMap<String, String>> getAnimePage(int page) {
        AnimeDAO animeDAO = new AnimeDAO(CONN);

        List<HashMap<String, String>> list = animeDAO.getAll().stream().sorted(Comparator.comparing(Anime::getStartDate).reversed()).map(this::map).toList();

        return list.subList((page - 1) * ITEMS_ON_PAGE, Math.min(page * ITEMS_ON_PAGE, list.size()));
    }

    public List<HashMap<String, String>> getAnimePage(int page, String q) {
        AnimeDAO animeDAO = new AnimeDAO(CONN);

        List<HashMap<String, String>> list = animeDAO.getAll().stream().sorted(Comparator.comparing(Anime::getStartDate).reversed()).filter((item) -> item.getTitle().contains(q)).map(this::map).toList();

        return list.subList((page - 1) * ITEMS_ON_PAGE, Math.min(page * ITEMS_ON_PAGE, list.size()));
    }
}
