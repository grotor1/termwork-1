package com.grotor.termwork1sem.servlets;

import com.google.common.hash.Hashing;
import com.grotor.termwork1sem.config.FreemarkerConfig;
import com.grotor.termwork1sem.connection.DevPostgressConnection;
import com.grotor.termwork1sem.dao.AccountDAO;
import com.grotor.termwork1sem.entities.Account;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegistartionServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> root = new HashMap<>();

        String error = req.getParameter("error");
        if (error != null) {
            switch (error) {
                case "name":
                    root.put("error", "Имя не может быть длиннее 30 символов");
                    break;
                case "missing":
                    root.put("error", "Какое-то из полей не заполнено");
                    break;
                case "repeat":
                    root.put("error", "Пароли не совпадают");
                    break;
                case "password":
                    root.put("error", "Пароль не подходит под условия (8 - 16 символов, цифры, большая и маленькая буква)");
                    break;
                case "login":
                    root.put("error", "Логин не подходит под условия (4 - 16 символов)");
                    break;
                case "loginUniq":
                    root.put("error", "Логин не уникальный");
                    break;
            }
        }

        Template tmpl = FreemarkerConfig.getInstance().getTemplate("reg.ftl");
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
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String passwordRep = req.getParameter("passwordRep");

        if (username.isEmpty() || name.isEmpty() || password.isEmpty() || passwordRep.isEmpty()) {
            resp.sendRedirect("registration?error=missing");
            return;
        }

        if (!password.equals(passwordRep)) {
            resp.sendRedirect("registration?error=repeat");
            return;
        }

        if (!password.matches(".*[0-9].*") || password.length() < 8 || password.length() > 16 || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*")) {
            System.out.println("" + !password.matches("[0-9]") + (password.length() < 8) + (password.length() > 16) + !password.matches("[A-Z]") + !password.matches("[a-z]"));
            resp.sendRedirect("registration?error=password");
            return;
        }

        if (name.length() > 30) {
            resp.sendRedirect("registration?error=name");
            return;
        }

        try {
            AccountDAO accountDAO = new AccountDAO(DevPostgressConnection.getInstance());
            Account account = new Account();
            String hashedPassword = Hashing.sha256()
                    .hashString(password, StandardCharsets.UTF_8)
                    .toString();

            account.setPassword(hashedPassword);
            account.setName(name);
            account.setUsername(username);
            account.setId(UUID.randomUUID());

            accountDAO.save(account);
            resp.sendRedirect("login");
        } catch (SQLException e) {
            resp.sendRedirect("registration?error=loginUniq");
        }
    }
}
