package com.grotor.termwork1sem.filters;

import com.grotor.termwork1sem.connection.DevPostgressConnection;
import com.grotor.termwork1sem.dao.AccountDAO;
import com.grotor.termwork1sem.entities.Account;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Cookie[] cookies = req.getCookies();

        if (req.getSession().getAttribute("account") == null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("user-token")) {
                    String v = c.getValue();
                    String username = v.split("@")[0];
                    String hashedPassword = v.split("@")[1];

                    AccountDAO accountDAO = new AccountDAO(DevPostgressConnection.getInstance());
                    Account account = accountDAO.getByName(username).orElse(new Account());

                    if (account.getPassword().equals(hashedPassword)) {
                        req.getSession().setAttribute("account", account);

                    }
                }
            }
        }

        chain.doFilter(req, res);
    }
}
