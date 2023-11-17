package com.grotor.termwork1sem.entities;

import com.grotor.termwork1sem.enums.StaffType;
import com.grotor.termwork1sem.enums.VAType;

public class Staff {
    private Person person;
    private StaffType staffType;

    public Staff(Person person, StaffType staffType) {
        this.person = person;
        this.staffType = staffType;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public StaffType getStaffType() {
        return staffType;
    }

    public void setStaffType(StaffType staffType) {
        this.staffType = staffType;
    }
}
