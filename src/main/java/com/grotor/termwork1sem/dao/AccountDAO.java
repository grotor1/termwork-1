package com.grotor.termwork1sem.dao;

import com.grotor.termwork1sem.entities.Account;
import com.grotor.termwork1sem.entities.Anime;
import com.grotor.termwork1sem.entities.AnimeListItem;
import com.grotor.termwork1sem.enums.AnimeListItemType;
import com.grotor.termwork1sem.helpers.DefaultValues;
import com.grotor.termwork1sem.mappers.AccountMapper;
import com.grotor.termwork1sem.mappers.AnimeMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccountDAO implements AbstractDAO<Account> {
    private final Connection CONN;
    //language=sql
    private final String SAVE = "insert into account(id, name, bio, photo, username, password) values (?, ?, ?, ?, ?, ?)";
    //language=sql
    private final String UPDATE = "update account set name = ?, bio = ?, photo = ? where id = ?";
    //language=sql
    private final String GET_ALL = "select * from account";
    //language=sql
    private final String GET = "select * from account where id = ?";
    //language=sql
    private final String DELETE = "delete from account where id = ?";
    //language=sql
    private final String GET_ANIME_LIST = "select anime.* from anime join account_anime on anime.id = account_anime.anime_id where account_anime.account_id = ?";
    //language=sql
    private final String GET_ANIME_LIST_TYPE = "select account_anime.relation_type from anime join account_anime on anime.id = account_anime.anime_id where account_anime.account_id = ?";
    //language=sql
    private final String GET_ID_BY_USERNAME = "select id from account where username = ?";
    //language=sql
    private final String DELETE_FROM_LIST = "delete from account_anime where account_anime.account_id = ?";
    //language=sql
    private final String ADD_TO_LIST = "insert into account_anime(id, anime_id, account_id, relation_type) values (?, ?, ?, ?::account_anime_relation_type)";


    public AccountDAO(Connection conn) {
        this.CONN = conn;
    }

    @Override
    public boolean save(Account entity) throws SQLException {
        PreparedStatement ps = CONN.prepareStatement(SAVE);
        ps.setObject(1, UUID.randomUUID());
        ps.setString(2, entity.getName());
        ps.setString(3, Optional.ofNullable(entity.getBio()).orElse(DefaultValues.DEFAULT_STRING));
        ps.setString(4, Optional.ofNullable(entity.getPhoto()).orElse(DefaultValues.DEFAULT_USER_PHOTO));
        ps.setString(5, entity.getUsername());
        ps.setString(6, entity.getPassword());
        return ps.execute();
    }

    @Override
    public boolean update(UUID id, Account entity) {
        try {
            PreparedStatement ps = CONN.prepareStatement(UPDATE);
            ps.setString(1, entity.getName());
            ps.setString(2, Optional.ofNullable(entity.getBio()).orElse(DefaultValues.DEFAULT_STRING));
            ps.setString(3, Optional.ofNullable(entity.getPhoto()).orElse(DefaultValues.DEFAULT_USER_PHOTO));
            ps.setObject(4, id);
            ps.executeUpdate();

            PreparedStatement delete = CONN.prepareStatement(DELETE_FROM_LIST);
            delete.setObject(1, id);
            delete.executeUpdate();

            entity.getAnimeList().forEach((item) -> {
                try {
                    PreparedStatement add = CONN.prepareStatement(ADD_TO_LIST);
                    add.setObject(1, UUID.randomUUID());
                    add.setObject(2, item.anime.getId());
                    add.setObject(3, id);
                    add.setObject(4, item.getType().text);
                    add.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Account> getAll() {
        try {
            PreparedStatement ps = CONN.prepareStatement(GET_ALL);
            return new AccountMapper().map(ps.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Account> get(UUID id) {
        try {
            PreparedStatement ps = CONN.prepareStatement(GET);
            ps.setObject(1, id);
            Account account = new AccountMapper().map(ps.executeQuery()).get(0);
            if (account != null) {
                List<AnimeListItem> ls = new ArrayList<>();

                PreparedStatement aps = CONN.prepareStatement(GET_ANIME_LIST);
                aps.setObject(1, id);
                ResultSet ars = aps.executeQuery();
                List<Anime> als = new AnimeMapper(CONN).map(ars);

                PreparedStatement rps = CONN.prepareStatement(GET_ANIME_LIST_TYPE);
                rps.setObject(1, id);
                ResultSet rrs = rps.executeQuery();

                als.forEach((anime) -> {
                    try {
                        rrs.next();
                        ls.add(new AnimeListItem(anime, AnimeListItemType.valueOfLabel(rrs.getString(1))));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                account.setAnimeList(ls);
                return Optional.of(account);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Account> getByName(String userName) {
        try {
            PreparedStatement ps = CONN.prepareStatement(GET_ID_BY_USERNAME);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return get(rs.getObject(1, UUID.class));
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
