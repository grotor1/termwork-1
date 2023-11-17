package com.grotor.termwork1sem.enums;

import java.util.HashMap;
import java.util.Map;

public enum StaffType {
    DIRECTOR("Режиссер"), COMPOSITOR("Композитор"), ANIMATOR("Аниматор"), VA("Сею");
    public final String text;

    private static final Map<String, StaffType> BY_LABEL = new HashMap<>();

    static {
        for (StaffType e: values()) {
            BY_LABEL.put(e.text, e);
        }
    }

    StaffType(String text) {
        this.text = text;
    }

    public static StaffType valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
