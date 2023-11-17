package com.grotor.termwork1sem.mappers;

import com.grotor.termwork1sem.dao.StudioDAO;
import com.grotor.termwork1sem.entities.Anime;
import com.grotor.termwork1sem.entities.Studio;
import com.grotor.termwork1sem.enums.AnimeStatus;
import com.grotor.termwork1sem.enums.AnimeType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AnimeMapper implements AbstractMapper<Anime> {
    private final Connection CONN;

    public AnimeMapper(Connection conn) {
        CONN = conn;
    }

    @Override
    public List<Anime> map(ResultSet rs) throws SQLException {
        List<Anime> ls = new ArrayList<>();
        while (rs.next()) {
            StudioDAO studioDAO = new StudioDAO(CONN);
            Anime anime = new Anime();
            anime.setId(rs.getObject(1, UUID.class));
            anime.setTitle(rs.getString(2));
            anime.setDescription(rs.getString(3));
            anime.setPhoto(rs.getString(4));
            anime.setStatus(AnimeStatus.valueOfLabel(rs.getString(5)));
            anime.setStartDate(rs.getDate(6));
            anime.setAnimeType(AnimeType.valueOfLabel(rs.getString(7)));
            anime.setPlannedEpisodes(rs.getInt(8));
            anime.setEpisodes(rs.getInt(9));
            anime.setRestriction(rs.getString(11));
            ls.add(anime);
        }
        return ls;
    }
}
