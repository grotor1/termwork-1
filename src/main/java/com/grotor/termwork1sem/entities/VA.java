package com.grotor.termwork1sem.entities;

import com.grotor.termwork1sem.enums.VAType;

public class VA {
    private Person person;
    private VAType vaType;

    public VA(Person person, VAType vaType) {
        this.person = person;
        this.vaType = vaType;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public VAType getVaType() {
        return vaType;
    }

    public void setVaType(VAType vaType) {
        this.vaType = vaType;
    }
}
