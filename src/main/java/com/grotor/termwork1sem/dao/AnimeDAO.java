package com.grotor.termwork1sem.dao;

import com.grotor.termwork1sem.entities.*;
import com.grotor.termwork1sem.entities.Character;
import com.grotor.termwork1sem.enums.AnimeListItemType;
import com.grotor.termwork1sem.enums.StaffType;
import com.grotor.termwork1sem.helpers.DefaultValues;
import com.grotor.termwork1sem.mappers.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AnimeDAO implements AbstractDAO<Anime>{
    private final Connection CONN;
    //language=sql
    private final String SAVE = "insert into anime(id, title, description, photo, status, start_date, anime_type, planned_episodes, episodes, studio_id, restriction) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    //language=sql
    private final String UPDATE = "update anime set title = ?, description = ?, photo = ?, status = ?, start_date = ?, anime_type = ?, planned_episodes = ?, episodes = ?, studio_id = ?, restriction = ? where id = ?";
    //language=sql
    private final String GET_ALL = "select * from anime";
    //language=sql
    private final String GET = "select * from anime where id = ?";
    //language=sql
    private final String DELETE = "delete from anime where id = ?";
    //language=sql
    private final String GET_CHARS = "select * from character where id in (select character_id from anime_character where anime_id = ?)";
    //language=sql
    private final String GET_STAFF = "select person.* from person join anime_person on person.id = anime_person.person_id where anime_person.anime_id = ?";
    //language=sql
    private final String GET_STAFF_TYPE = "select anime_person.relation_type from person join anime_person on person.id = anime_person.person_id where anime_person.anime_id = ?";
    //language=sql
    private final String GET_STUDIO = "select studio.* from studio, anime where studio.id = anime.studio_id and anime.id = ?";

    public AnimeDAO(Connection conn) {
        this.CONN = conn;
    }

    @Override
    public boolean save(Anime entity) {
        try {
            PreparedStatement ps = CONN.prepareStatement(SAVE);
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, entity.getTitle());
            ps.setString(3, Optional.ofNullable(entity.getDescription()).orElse(DefaultValues.DEFAULT_STRING));
            ps.setString(4, Optional.ofNullable(entity.getPhoto()).orElse(DefaultValues.DEFAULT_ANIME_PHOTO));
            ps.setString(5, Optional.ofNullable(entity.getStatus()).orElse(DefaultValues.DEFAULT_ANIME_STATUS).text);
            ps.setDate(6, Optional.ofNullable(entity.getStartDate()).orElse(DefaultValues.DEFAULT_DATE));
            ps.setString(7, entity.getAnimeType().text);
            ps.setInt(8, entity.getPlannedEpisodes());
            ps.setInt(9, entity.getEpisodes());
            ps.setObject(10, entity.getStudio().getId());
            ps.setString(11, Optional.ofNullable(entity.getRestriction()).orElse(DefaultValues.DEFAULT_STRING));
            return ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(UUID id, Anime entity) {
        try {
            PreparedStatement ps = CONN.prepareStatement(UPDATE);
            ps.setString(1, entity.getTitle());
            ps.setString(2, Optional.ofNullable(entity.getDescription()).orElse(DefaultValues.DEFAULT_STRING));
            ps.setString(3, Optional.ofNullable(entity.getPhoto()).orElse(DefaultValues.DEFAULT_ANIME_PHOTO));
            ps.setString(4, Optional.ofNullable(entity.getStatus()).orElse(DefaultValues.DEFAULT_ANIME_STATUS).text);
            ps.setDate(5, Optional.ofNullable(entity.getStartDate()).orElse(DefaultValues.DEFAULT_DATE));
            ps.setString(6, entity.getAnimeType().text);
            ps.setInt(7, entity.getPlannedEpisodes());
            ps.setInt(8, entity.getEpisodes());
            ps.setObject(9, entity.getStudio().getId());
            ps.setString(10, Optional.ofNullable(entity.getRestriction()).orElse(DefaultValues.DEFAULT_STRING));ps.setObject(4, id);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Anime> getAll() {
        try {
            PreparedStatement ps = CONN.prepareStatement(GET_ALL);
            return new AnimeMapper(CONN).map(ps.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Anime> get(UUID id) {
        try {
            PreparedStatement ps = CONN.prepareStatement(GET);
            ps.setObject(1, id);
            Anime anime = new AnimeMapper(CONN).map(ps.executeQuery()).get(0);
            if (anime == null) {
                return Optional.empty();
            } else {
                PreparedStatement ps2 = CONN.prepareStatement(GET_STUDIO);
                ps2.setObject(1, id);
                Studio studio = new StudioMapper().map(ps2.executeQuery()).get(0);
                anime.setStudio(studio);

                List<Staff> sls = new ArrayList<>();

                PreparedStatement aps = CONN.prepareStatement(GET_STAFF);
                aps.setObject(1, id);
                ResultSet srs = aps.executeQuery();
                List<Person> pls = new PersonMapper().map(srs);

                PreparedStatement rps = CONN.prepareStatement(GET_STAFF_TYPE);
                rps.setObject(1, id);
                ResultSet rrs = rps.executeQuery();

                pls.forEach((person) -> {
                    try {
                        rrs.next();
                        sls.add(new Staff(person, StaffType.valueOfLabel(rrs.getString(1))));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                anime.setStaff(sls);


                PreparedStatement cps = CONN.prepareStatement(GET_CHARS);
                cps.setObject(1, id);
                ResultSet crs = cps.executeQuery();
                List<Character> cls = new CharacterMapper().map(crs);

                anime.setCharacters(cls);

                return Optional.of(anime);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(UUID id) {
        try {
            PreparedStatement ps = CONN.prepareStatement(DELETE);
            ps.setObject(1, id);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
