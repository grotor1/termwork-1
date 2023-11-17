package com.grotor.termwork1sem.services;

import com.grotor.termwork1sem.entities.Account;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class UserInfo {
    public static void addUserInfo (HttpSession session, Map<String, Object> root) {
        Account account = (Account) session.getAttribute("account");

        if (account != null) {
            root.put("username", account.getUsername());
            root.put("userid", account.getId());
        }
    }
}
