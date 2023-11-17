package com.grotor.termwork1sem.servlets;

import com.grotor.termwork1sem.config.FreemarkerConfig;
import com.grotor.termwork1sem.connection.DevPostgressConnection;
import com.grotor.termwork1sem.dao.AccountDAO;
import com.grotor.termwork1sem.dao.AnimeDAO;
import com.grotor.termwork1sem.entities.Account;
import com.grotor.termwork1sem.entities.Anime;
import com.grotor.termwork1sem.entities.AnimeListItem;
import com.grotor.termwork1sem.enums.AnimeListItemType;
import com.grotor.termwork1sem.helpers.DateUtils;
import com.grotor.termwork1sem.helpers.FileUtils;
import com.grotor.termwork1sem.helpers.MultipartUtils;
import com.grotor.termwork1sem.services.UserInfo;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class EditMePageServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        Account account = (Account) req.getSession().getAttribute("account");

        Map<String, Object> root = new HashMap<>();

        UserInfo.addUserInfo(req.getSession(), root);

        root.put("pageName", "Редактирование профиля");

        root.put("name", account.getName());
        root.put("bio", account.getBio());

        Template tmpl = FreemarkerConfig.getInstance().getTemplate("editMe.ftl");
        try {
            tmpl.process(root, resp.getWriter());
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        AnimeDAO animeDAO = new AnimeDAO(DevPostgressConnection.getInstance());
        String name = new BufferedReader(new InputStreamReader(req.getPart("name").getInputStream()))
                .lines().collect(Collectors.joining("\n"));
        String bio = new BufferedReader(new InputStreamReader(req.getPart("bio").getInputStream()))
                .lines().collect(Collectors.joining("\n"));
        String list = new BufferedReader(new InputStreamReader(req.getPart("list").getInputStream()))
                .lines().collect(Collectors.joining("\n"));
        String filename = null;
        if (req.getPart("photo") != null) {
            filename = MultipartUtils.getFilename(req.getPart("photo"));
            FileUtils.saveFile(filename, req.getPart("photo").getInputStream().readAllBytes());
        }

        List<AnimeListItem> animeListItems = Arrays.stream(list.split(",")).map((entry) -> {
            String[] keyValue = entry.split(":");
            Anime anime = animeDAO.get(UUID.fromString(keyValue[0])).orElse(new Anime());
            AnimeListItemType type = AnimeListItemType.valueOfLabel(keyValue[1]);
            return new AnimeListItem(anime, type);
        }).toList();

        Account account = (Account) req.getSession().getAttribute("account");

        account.setName(name);
        account.setBio(bio);
        account.setAnimeList(animeListItems);
        if (filename != null) {
            account.setPhoto(filename);
        }

        new AccountDAO(DevPostgressConnection.getInstance()).update(account.getId(), account);
    }
}

