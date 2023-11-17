package com.grotor.termwork1sem.enums;


import java.util.HashMap;
import java.util.Map;

public enum AnimeStatus {
    ONGOING("Онгоинг"), ANNOUNCED("Анонсировано"), FROZEN("Заморожено"), ENDED("Закончено");

    private static final Map<String, AnimeStatus> BY_LABEL = new HashMap<>();

    static {
        for (AnimeStatus e: values()) {
            BY_LABEL.put(e.text, e);
        }
    }

    public final String text;

    AnimeStatus(String text) {
        this.text = text;
    }
    public static AnimeStatus valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
