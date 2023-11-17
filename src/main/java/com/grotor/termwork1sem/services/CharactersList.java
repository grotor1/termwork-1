package com.grotor.termwork1sem.services;

import com.grotor.termwork1sem.dao.CharacterDAO;
import com.grotor.termwork1sem.entities.Character;

import java.sql.Connection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class CharactersList {
    private final Connection CONN;
    private static final int ITEMS_ON_PAGE = 14;

    public CharactersList(Connection conn) {
        CONN = conn;
    }

    public int getPageCount() {
        CharacterDAO characterDAO = new CharacterDAO(CONN);

        return (int) Math.ceil((double) characterDAO.getAll().size() / ITEMS_ON_PAGE);
    }

    public int getPageCount(String q) {
        CharacterDAO characterDAO = new CharacterDAO(CONN);

        return Math.max((int) Math.ceil((double) characterDAO.getAll().stream().filter((item) -> item.getName().contains(q)).toList().size() / ITEMS_ON_PAGE), 1);
    }

    private HashMap<String, String> map(Character item) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", item.getId().toString());
        map.put("img", item.getPhoto());
        map.put("name", item.getName());
        return map;
    }

    public List<HashMap<String, String>> getCharacterPage(int page) {
        CharacterDAO characterDAO = new CharacterDAO(CONN);

        List<HashMap<String, String>> list = characterDAO.getAll().stream().sorted(Comparator.comparing(Character::getName)).map(this::map).toList();

        return list.subList((page - 1) * ITEMS_ON_PAGE, Math.min(page * ITEMS_ON_PAGE, list.size()));
    }

    public List<HashMap<String, String>> getCharacterPage(int page, String q) {
        CharacterDAO personDAO = new CharacterDAO(CONN);

        List<HashMap<String, String>> list = personDAO.getAll().stream().sorted(Comparator.comparing(Character::getName)).filter((item) -> item.getName().contains(q)).map(this::map).toList();

        return list.subList((page - 1) * ITEMS_ON_PAGE, Math.min(page * ITEMS_ON_PAGE, list.size()));
    }
}
