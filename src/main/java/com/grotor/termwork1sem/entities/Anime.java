package com.grotor.termwork1sem.entities;

import com.grotor.termwork1sem.enums.AnimeStatus;
import com.grotor.termwork1sem.enums.AnimeType;
import com.grotor.termwork1sem.helpers.PhotoUtils;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class Anime   {
    private UUID id;
    private String title;
    private String description;
    private String photo;
    private AnimeStatus status;
    private Date startDate;
    private AnimeType animeType;
    private int plannedEpisodes;
    private int episodes;
    private Studio studio;
    private String restriction;
    private List<Character> characters;
    private List<Staff> staff;

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public AnimeStatus getStatus() {
        return status;
    }

    public void setStatus(AnimeStatus status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public AnimeType getAnimeType() {
        return animeType;
    }

    public void setAnimeType(AnimeType animeType) {
        this.animeType = animeType;
    }

    public int getPlannedEpisodes() {
        return plannedEpisodes;
    }

    public void setPlannedEpisodes(int plannedEpisodes) {
        this.plannedEpisodes = plannedEpisodes;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public String getRestriction() {
        return restriction;
    }

    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

}
