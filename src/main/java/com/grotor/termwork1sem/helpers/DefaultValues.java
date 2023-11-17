package com.grotor.termwork1sem.helpers;

import com.grotor.termwork1sem.enums.AnimeStatus;

import java.sql.Date;

public class DefaultValues {
    public static final String DEFAULT_STRING = "";
    public static final String DEFAULT_USER_PHOTO = "placeholder-person.png";
    public static final String DEFAULT_ANIME_PHOTO = "placeholder-anime.png";
    public static final String DEFAULT_POST_PHOTO = "placeholder-post.png";
    public static final AnimeStatus DEFAULT_ANIME_STATUS = AnimeStatus.ANNOUNCED;
    public static final Date DEFAULT_DATE = Date.valueOf("0001-01-01");
    public static final int DEFAULT_INT = -1;
}
