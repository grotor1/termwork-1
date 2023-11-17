package com.grotor.termwork1sem.servlets;

import com.grotor.termwork1sem.config.FreemarkerConfig;
import com.grotor.termwork1sem.connection.DevPostgressConnection;
import com.grotor.termwork1sem.dao.AccountDAO;
import com.grotor.termwork1sem.entities.Account;
import com.grotor.termwork1sem.helpers.DateUtils;
import com.grotor.termwork1sem.services.UserInfo;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class UserPageServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        AccountDAO accountDAO = new AccountDAO(DevPostgressConnection.getInstance());
        Account account = accountDAO.get(UUID.fromString(req.getParameter("id"))).orElse(new Account());

        Map<String, Object> root = new HashMap<>();

        UserInfo.addUserInfo(req.getSession(), root);

        root.put("pageName", "Профиль");

        Map<String, String> fields = new HashMap<>();

        fields.put("Имя пользователя", account.getUsername());
        fields.put("Имя", account.getName());
        fields.put("О себе", account.getBio());
        fields.put("Дата регистрации", DateUtils.getFullDate(account.getRegistrationDate()));

        root.put("fields", fields);

        root.put("img", account.getPhoto());

        Map<String, Map<String, Object>> lists = new HashMap<>();

        Map<String, Object> animeList = new HashMap<>();

        animeList.put("url", "anime");
        animeList.put("list", account.getAnimeList().stream().map((item) -> {
            HashMap<String, Object> itemHash = new HashMap<>();
            itemHash.put("id", item.anime.getId());
            itemHash.put("img", item.anime.getPhoto());

            List<String> animeFields = new ArrayList<>();
            animeFields.add(item.anime.getTitle());
            animeFields.add(item.type.text);

            itemHash.put("fields", animeFields);
            return itemHash;
        }).toList());

        lists.put("Список просмотренного", animeList);

        root.put("lists", lists);

        String template = ((Account) req.getSession().getAttribute("account")).getId().toString().equals(req.getParameter("id")) ?
                "myPage.ftl" : "entity.ftl";

        Template tmpl = FreemarkerConfig.getInstance().getTemplate(template);
        try {
            tmpl.process(root, resp.getWriter());
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}

