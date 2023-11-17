package com.grotor.termwork1sem.entities;

import com.grotor.termwork1sem.enums.StaffType;

public class MakingWork {
    private Anime anime;
    private StaffType staffType;

    public MakingWork(Anime anime, StaffType staffType) {
        this.anime = anime;
        this.staffType = staffType;
    }

    public StaffType getStaffType() {
        return staffType;
    }

    public void setStaffType(StaffType staffType) {
        this.staffType = staffType;
    }

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }
}
