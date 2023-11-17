package com.grotor.termwork1sem.entities;

import com.grotor.termwork1sem.helpers.PhotoUtils;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class Person   {
    private UUID id;
    private String name;
    private String description;
    private String photo;
    private Date birthdate;

    private List<MakingWork> works;
    private List<VoicingWork> voicing;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return PhotoUtils.getPhotoUrl(photo);
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public List<MakingWork> getWorks() {
        return works;
    }

    public void setWorks(List<MakingWork> works) {
        this.works = works;
    }

    public List<VoicingWork> getVoicing() {
        return voicing;
    }

    public void setVoicing(List<VoicingWork> voicing) {
        this.voicing = voicing;
    }
}
