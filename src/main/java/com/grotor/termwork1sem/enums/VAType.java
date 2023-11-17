package com.grotor.termwork1sem.enums;

import java.util.HashMap;
import java.util.Map;

public enum VAType {
    VA("Сею"), VA_EN("Сею (EN)"), VA_RU("Сею (RU)");
    public final String text;

    private static final Map<String, VAType> BY_LABEL = new HashMap<>();

    static {
        for (VAType e: values()) {
            BY_LABEL.put(e.text, e);
        }
    }

    VAType(String text) {
        this.text = text;
    }

    public static VAType valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
