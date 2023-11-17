package com.grotor.termwork1sem.entities;

import com.grotor.termwork1sem.helpers.PhotoUtils;

import java.util.List;
import java.util.UUID;

public class Character   {
    private UUID id;
    private String name;
    private String description;
    private String photo;
    private List<VA> VAs;
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

    public List<VA> getVAs() {
        return VAs;
    }

    public void setVAs(List<VA> VAs) {
        this.VAs = VAs;
    }

}
