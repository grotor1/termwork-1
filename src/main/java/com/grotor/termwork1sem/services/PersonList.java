package com.grotor.termwork1sem.services;

import com.grotor.termwork1sem.dao.AnimeDAO;
import com.grotor.termwork1sem.dao.PersonDAO;
import com.grotor.termwork1sem.entities.Anime;
import com.grotor.termwork1sem.entities.Person;
import com.grotor.termwork1sem.helpers.DateUtils;

import java.sql.Connection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class PersonList {
    private final Connection CONN;
    private static final int ITEMS_ON_PAGE = 14;

    public PersonList(Connection conn) {
        CONN = conn;
    }

    public int getPageCount() {
        PersonDAO personDAO = new PersonDAO(CONN);

        return (int) Math.ceil((double) personDAO.getAll().size() / ITEMS_ON_PAGE);
    }

    public int getPageCount(String q) {
        PersonDAO personDAO = new PersonDAO(CONN);

        return Math.max((int) Math.ceil((double) personDAO.getAll().stream().filter((item) -> item.getName().contains(q)).toList().size() / ITEMS_ON_PAGE), 1);
    }

    private HashMap<String, String> map(Person item) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", item.getId().toString());
        map.put("img", item.getPhoto());
        map.put("name", item.getName());
        return map;
    }

    public List<HashMap<String, String>> getPersonPage(int page) {
        PersonDAO personDAO = new PersonDAO(CONN);

        List<HashMap<String, String>> list = personDAO.getAll().stream().sorted(Comparator.comparing(Person::getName)).map(this::map).toList();

        return list.subList((page - 1) * ITEMS_ON_PAGE, Math.min(page * ITEMS_ON_PAGE, list.size()));
    }

    public List<HashMap<String, String>> getPersonPage(int page, String q) {
        PersonDAO personDAO = new PersonDAO(CONN);

        List<HashMap<String, String>> list = personDAO.getAll().stream().sorted(Comparator.comparing(Person::getName)).filter((item) -> item.getName().contains(q)).map(this::map).toList();

        return list.subList((page - 1) * ITEMS_ON_PAGE, Math.min(page * ITEMS_ON_PAGE, list.size()));
    }
}
