package com.grotor.termwork1sem.entities;

import com.grotor.termwork1sem.enums.VAType;

public class VoicingWork {
    private Character character;
    private VAType vaType;

    public VoicingWork(Character character, VAType vaType) {
        this.character = character;
        this.vaType = vaType;
    }

    public VAType getVaType() {
        return vaType;
    }

    public void setVaType(VAType vaType) {
        this.vaType = vaType;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
