package com.grotor.termwork1sem.mappers;

import com.grotor.termwork1sem.entities.Account;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccountMapper implements AbstractMapper<Account> {
    @Override
    public List<Account> map(ResultSet rs) throws SQLException {
        List<Account> ls = new ArrayList<>();
        while (rs.next()) {
            Account account = new Account();
            account.setId(rs.getObject(1, UUID.class));
            account.setRegistrationDate(rs.getDate(2));
            account.setName(rs.getString(3));
            account.setPhoto(rs.getString(4));
            account.setBio(rs.getString(5));
            account.setUsername(rs.getString(6));
            account.setPassword(rs.getString(7));
            ls.add(account);
        }
        return ls;
    }
}

