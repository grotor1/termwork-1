package com.grotor.termwork1sem.dao;

import com.grotor.termwork1sem.entities.Anime;
import com.grotor.termwork1sem.entities.Person;
import com.grotor.termwork1sem.entities.Character;
import com.grotor.termwork1sem.entities.VA;
import com.grotor.termwork1sem.enums.VAType;
import com.grotor.termwork1sem.helpers.DefaultValues;
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

public class CharacterDAO implements AbstractDAO<Character>{
    private final Connection CONN;
    //language=sql
    private final String SAVE = "insert into character(id, name, description, photo) values (?, ?, ?, ?)";
    //language=sql
    private final String UPDATE = "update character set name = ?, description = ?, photo = ? where id = ?";
    //language=sql
    private final String GET_ALL = "select * from character";
    //language=sql
    private final String GET = "select * from character where id = ?";
    //language=sql
    private final String DELETE = "delete from character where id = ?";
    //language=sql
    private final String GET_ANIME = "select * from anime where id in (select anime_id from anime_character where character_id = ?)";
    //language=sql
    private final String GET_VA = "select person.* from person join person_character on person.id = person_character.person_id where person_character.character_id = ?";
    //language=sql
    private final String GET_VA_TYPE = "select person_character.relation_type from person join person_character on person.id = person_character.person_id where person_character.character_id = ?";


    public CharacterDAO(Connection conn) {
        this.CONN = conn;
    }

    @Override
    public boolean save(Character entity) {
        try {
            PreparedStatement ps = CONN.prepareStatement(SAVE);
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, entity.getName());
            ps.setString(3, Optional.ofNullable(entity.getDescription()).orElse(DefaultValues.DEFAULT_STRING));
            ps.setString(4, Optional.ofNullable(entity.getPhoto()).orElse(DefaultValues.DEFAULT_USER_PHOTO));
            return ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(UUID id, Character entity) {
        try {
            PreparedStatement ps = CONN.prepareStatement(UPDATE);
            ps.setString(1, entity.getName());
            ps.setString(2, Optional.ofNullable(entity.getDescription()).orElse(DefaultValues.DEFAULT_STRING));
            ps.setString(3, Optional.ofNullable(entity.getPhoto()).orElse(DefaultValues.DEFAULT_USER_PHOTO));
            ps.setObject(4, id);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Character> getAll() {
        try {
            PreparedStatement ps = CONN.prepareStatement(GET_ALL);
            return new CharacterMapper().map(ps.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Character> get(UUID id) {
        try {
            PreparedStatement ps = CONN.prepareStatement(GET);
            ps.setObject(1, id);
            Character character = new CharacterMapper().map(ps.executeQuery()).get(0);
            if (character == null) {
                return Optional.empty();
            } else {
                List<VA> sls = new ArrayList<>();

                PreparedStatement vaps = CONN.prepareStatement(GET_VA);
                vaps.setObject(1, id);
                ResultSet srs = vaps.executeQuery();
                List<Person> pls = new PersonMapper().map(srs);

                PreparedStatement rps = CONN.prepareStatement(GET_VA_TYPE);
                rps.setObject(1, id);
                ResultSet rrs = rps.executeQuery();

                pls.forEach((person) -> {
                    try {
                        rrs.next();
                        sls.add(new VA(person, VAType.valueOfLabel(rrs.getString(1))));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                character.setVAs(sls);


                PreparedStatement aps = CONN.prepareStatement(GET_ANIME);
                aps.setObject(1, id);
                ResultSet crs = aps.executeQuery();
                List<Anime> cls = new AnimeMapper(CONN).map(crs);

                character.setAnime(cls);

                return Optional.of(character);
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
