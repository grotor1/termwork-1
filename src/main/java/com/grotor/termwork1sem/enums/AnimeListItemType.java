package com.grotor.termwork1sem.enums;

import java.util.HashMap;
import java.util.Map;

public enum AnimeListItemType {
    WATCHING("Смотрю"), WATCHED("Просмотренно"), STOPPED("Брошенно"), FAVOURITE("Любимое"), PLANNED("В планах");

    private static final Map<String, AnimeListItemType> BY_LABEL = new HashMap<>();

    static {
        for (AnimeListItemType e: values()) {
            BY_LABEL.put(e.text, e);
        }
    }

    public final String text;

    AnimeListItemType(String text) {
        this.text = text;
    }

    public static AnimeListItemType valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
