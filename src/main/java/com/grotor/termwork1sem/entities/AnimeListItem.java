package com.grotor.termwork1sem.entities;

import com.grotor.termwork1sem.enums.AnimeListItemType;

public class AnimeListItem {
    public Anime anime;
    public AnimeListItemType type;

    public AnimeListItem(Anime anime, AnimeListItemType type) {
        this.anime = anime;
        this.type = type;
    }

    @Override
    public String toString() {
        return "AnimeListItem{" +
                "anime=" + anime.getTitle() +
                ", type=" + type.text +
                '}';
    }

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public AnimeListItemType getType() {
        return type;
    }

    public void setType(AnimeListItemType type) {
        this.type = type;
    }
}
