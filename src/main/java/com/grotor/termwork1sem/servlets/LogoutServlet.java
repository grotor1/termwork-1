package com.grotor.termwork1sem.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        session.removeAttribute("account");

        Cookie cookie = new Cookie("user-token", "");
        cookie.setMaxAge(0);

        resp.addCookie(cookie);

        resp.sendRedirect("./");
    }
}
