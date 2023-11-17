package com.grotor.termwork1sem;

import com.grotor.termwork1sem.connection.DevPostgressConnection;
import com.grotor.termwork1sem.dao.AccountDAO;
import com.grotor.termwork1sem.entities.Account;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        AccountDAO accountDAO = new AccountDAO(DevPostgressConnection.getInstance());
        System.out.println("V23v10v2004".matches(".*[0-9].*"));
    }
}
