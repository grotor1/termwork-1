package com.grotor.termwork1sem.entities;

import com.grotor.termwork1sem.helpers.PhotoUtils;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class Account {
    private UUID id;
    private Date registrationDate;
    private String name;
    private String photo;
    private String bio;
    private String username;
    private String password;

    public List<AnimeListItem> getAnimeList() {
        return animeList;
    }

    public void setAnimeList(List<AnimeListItem> animeList) {
        this.animeList = animeList;
    }

    private List<AnimeListItem> animeList;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return PhotoUtils.getPhotoUrl(photo);
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
