package com.grotor.termwork1sem.servlets;

import com.google.common.hash.Hashing;
import com.grotor.termwork1sem.config.FreemarkerConfig;
import com.grotor.termwork1sem.connection.DevPostgressConnection;
import com.grotor.termwork1sem.dao.AccountDAO;
import com.grotor.termwork1sem.entities.Account;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.checkerframework.checker.units.qual.A;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> root = new HashMap<>();

        String error = req.getParameter("error");
        if (error != null) {
            switch (error) {
                case "wrong":
                    root.put("error", "Пароль или имя пользователя неправильные");
                    break;
                case "missing":
                    root.put("error", "Какое-то из полей не заполнено");
                    break;
            }
        }

        Template tmpl = FreemarkerConfig.getInstance().getTemplate("login.ftl");
        try {
            tmpl.process(root, resp.getWriter());
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("login");
        String password = req.getParameter("password");
        boolean remember = req.getParameter("remember") != null;

        if (username.isEmpty() || password.isEmpty()) {
            resp.sendRedirect("login?error=missing");
            return;
        }

        AccountDAO accountDAO = new AccountDAO(DevPostgressConnection.getInstance());
        Account account = accountDAO.getByName(username).orElse(new Account());

        String hashedPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();

        if (account.getPassword().equals(hashedPassword)) {
            HttpSession session = req.getSession();
            session.setAttribute("account", account);

            if (remember) {
                Cookie rememberMeCookie = new Cookie("user-token", username + "@" + hashedPassword);
                rememberMeCookie.setMaxAge(60*60*24*365);
                resp.addCookie(rememberMeCookie);
            }

            resp.sendRedirect("./");
        } else {
            resp.sendRedirect("login?error=wrong");
        }
    }
}
