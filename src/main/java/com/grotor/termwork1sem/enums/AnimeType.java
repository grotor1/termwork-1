package com.grotor.termwork1sem.enums;

import java.util.HashMap;
import java.util.Map;

public enum AnimeType {
    OVA("OVA"), ONA("ONA"), TV("TV"), FILM("Фильм"), SPECIAL("Спешиал");
    public final String text;

    private static final Map<String, AnimeType> BY_LABEL = new HashMap<>();

    static {
        for (AnimeType e: values()) {
            BY_LABEL.put(e.text, e);
        }
    }

    AnimeType(String text) {
        this.text = text;
    }

    public static AnimeType valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
