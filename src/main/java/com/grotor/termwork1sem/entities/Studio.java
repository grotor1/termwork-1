package com.grotor.termwork1sem.entities;

import com.grotor.termwork1sem.helpers.PhotoUtils;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class Studio   {
    private UUID id;
    private String name;
    private String description;
    private String photo;
    private Date foundationDate;
    private List<Anime> anime;


    public List<Anime> getAnime() {
        return anime;
    }

    public void setAnime(List<Anime> anime) {
        this.anime = anime;
    }

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

    public Date getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(Date foundationDate) {
        this.foundationDate = foundationDate;
    }

}
