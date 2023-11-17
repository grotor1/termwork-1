package com.grotor.termwork1sem.dao;

import com.grotor.termwork1sem.entities.*;
import com.grotor.termwork1sem.entities.Character;
import com.grotor.termwork1sem.enums.StaffType;
import com.grotor.termwork1sem.enums.VAType;
import com.grotor.termwork1sem.helpers.DefaultValues;
import com.grotor.termwork1sem.mappers.AccountMapper;
import com.grotor.termwork1sem.mappers.AnimeMapper;
import com.grotor.termwork1sem.mappers.CharacterMapper;
import com.grotor.termwork1sem.mappers.PersonMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PersonDAO implements AbstractDAO<Person>{
    private final Connection CONN;
    //language=sql
    private final String SAVE = "insert into person(id, name, description, photo, birthdate) values (?, ?, ?, ?, ?)";
    //language=sql
    private final String UPDATE = "update person set name = ?, description = ?, photo = ?, birthdate = ? where id = ?";
    //language=sql
    private final String GET_ALL = "select * from person";
    //language=sql
    private final String GET = "select * from person where id = ?";
    //language=sql
    private final String DELETE = "delete from person where id = ?";
    //language=sql
    private final String GET_ANIME_WORK = "select anime.* from anime join anime_person on anime.id = anime_person.anime_id where anime_person.person_id = ?";
    //language=sql
    private final String GET_ANIME_WORK_TYPE = "select anime_person.relation_type from anime join anime_person on anime.id = anime_person.anime_id where anime_person.person_id = ?";
    //language=sql
    private final String GET_VOICING_WORK = "select character.* from character join person_character on character.id = person_character.character_id where person_character.person_id = ?";
    //language=sql
    private final String GET_VOICING_WORK_TYPE = "select person_character.relation_type from character join person_character on character.id = person_character.character_id where person_character.person_id = ?";


    public PersonDAO(Connection conn) {
        this.CONN = conn;
    }

    @Override
    public boolean save(Person entity) {
        try {
            PreparedStatement ps = CONN.prepareStatement(SAVE);
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, entity.getName());
            ps.setString(3, Optional.ofNullable(entity.getDescription()).orElse(DefaultValues.DEFAULT_STRING));
            ps.setString(4, Optional.ofNullable(entity.getPhoto()).orElse(DefaultValues.DEFAULT_USER_PHOTO));
            ps.setDate(5, Optional.ofNullable(entity.getBirthdate()).orElse(DefaultValues.DEFAULT_DATE));
            return ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(UUID id, Person entity) {
        try {
            PreparedStatement ps = CONN.prepareStatement(UPDATE);
            ps.setString(1, entity.getName());
            ps.setString(2, Optional.ofNullable(entity.getDescription()).orElse(DefaultValues.DEFAULT_STRING));
            ps.setString(3, Optional.ofNullable(entity.getPhoto()).orElse(DefaultValues.DEFAULT_USER_PHOTO));
            ps.setDate(4, Optional.ofNullable(entity.getBirthdate()).orElse(DefaultValues.DEFAULT_DATE));
            ps.setObject(5, id);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Person> getAll() {
        try {
            PreparedStatement ps = CONN.prepareStatement(GET_ALL);
            return new PersonMapper().map(ps.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Person> get(UUID id) {
        try {
            PreparedStatement ps = CONN.prepareStatement(GET);
            ps.setObject(1, id);
            Person person = new PersonMapper().map(ps.executeQuery()).get(0);
            if (person == null) {
                return Optional.empty();
            } else {
                List<VoicingWork> vwls = new ArrayList<>();

                PreparedStatement cps = CONN.prepareStatement(GET_VOICING_WORK);
                cps.setObject(1, id);
                ResultSet crs = cps.executeQuery();
                List<Character> cls = new CharacterMapper().map(crs);

                PreparedStatement crps = CONN.prepareStatement(GET_VOICING_WORK_TYPE);
                crps.setObject(1, id);
                ResultSet crrs = crps.executeQuery();

                cls.forEach((character) -> {
                    try {
                        crrs.next();
                        vwls.add(new VoicingWork(character, VAType.valueOfLabel(crrs.getString(1))));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                person.setVoicing(vwls);

                List<MakingWork> sls = new ArrayList<>();

                PreparedStatement aps = CONN.prepareStatement(GET_ANIME_WORK);
                aps.setObject(1, id);
                ResultSet ars = aps.executeQuery();
                List<Anime> als = new AnimeMapper(CONN).map(ars);

                PreparedStatement arps = CONN.prepareStatement(GET_ANIME_WORK_TYPE);
                arps.setObject(1, id);
                ResultSet arrs = arps.executeQuery();

                als.forEach((anime) -> {
                    try {
                        arrs.next();
                        sls.add(new MakingWork(anime, StaffType.valueOfLabel(arrs.getString(1))));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                person.setWorks(sls);

                return Optional.of(person);
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
