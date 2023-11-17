package com.grotor.termwork1sem.servlets;

import com.grotor.termwork1sem.config.FreemarkerConfig;
import com.grotor.termwork1sem.connection.DevPostgressConnection;
import com.grotor.termwork1sem.dao.AnimeDAO;
import com.grotor.termwork1sem.entities.Anime;
import com.grotor.termwork1sem.helpers.DateUtils;
import com.grotor.termwork1sem.helpers.DefaultValues;
import com.grotor.termwork1sem.services.CommentsList;
import com.grotor.termwork1sem.services.UserInfo;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class AnimeServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        AnimeDAO animeDAO = new AnimeDAO(DevPostgressConnection.getInstance());
        Anime anime = animeDAO.get(UUID.fromString(req.getParameter("id"))).orElse(new Anime());

        Map<String, Object> root = new HashMap<>();

        UserInfo.addUserInfo(req.getSession(), root);

        root.put("pageName", "Аниме");

        Map<String, Object> fields = new HashMap<>();

        fields.put("Название", anime.getTitle());

        fields.put("Тип", anime.getAnimeType().text);

        if (!anime.getStartDate().equals(DefaultValues.DEFAULT_DATE)) {
            fields.put("Дата выхода", DateUtils.getFullDate(anime.getStartDate()));
        }

        fields.put("Статус", anime.getStatus().text);

        String episodes = String.valueOf(anime.getEpisodes());
        if (anime.getPlannedEpisodes() != DefaultValues.DEFAULT_INT) {
            episodes += "/" + anime.getPlannedEpisodes();
        }
        fields.put("Эпизодов", episodes);

        if (!anime.getRestriction().equals(DefaultValues.DEFAULT_STRING)) {
            fields.put("Возрастное ограничение", anime.getRestriction());
        }


        fields.put("Описание", anime.getDescription());

        HashMap<String, String> studio = new HashMap<>();

        studio.put("id", anime.getStudio().getId().toString());
        studio.put("link", "studio");
        studio.put("text", anime.getTitle());

        fields.put("Студия", studio);

        root.put("fields", fields);

        root.put("img", anime.getPhoto());

        Map<String, Map<String, Object>> lists = new HashMap<>();

        if (!anime.getStaff().isEmpty()) {
            Map<String, Object> staffList = new HashMap<>();

            staffList.put("url", "person");
            staffList.put("list", anime.getStaff().stream().map((item) -> {
                HashMap<String, Object> itemHash = new HashMap<>();
                itemHash.put("id", item.getPerson().getId());
                itemHash.put("img", item.getPerson().getPhoto());

                List<String> personFields = new ArrayList<>();
                personFields.add(item.getPerson().getName());
                personFields.add(item.getStaffType().text);

                itemHash.put("fields", personFields);
                return itemHash;
            }).toList());

            lists.put("Создатели", staffList);
        }

        if (!anime.getCharacters().isEmpty()) {
            Map<String, Object> characterList = new HashMap<>();

            characterList.put("url", "character");
            characterList.put("list", anime.getCharacters().stream().map((item) -> {
                HashMap<String, Object> itemHash = new HashMap<>();
                itemHash.put("id", item.getId());
                itemHash.put("img", item.getPhoto());

                List<String> animeFields = new ArrayList<>();
                animeFields.add(item.getName());

                itemHash.put("fields", animeFields);
                return itemHash;
            }).toList());

            lists.put("Персонажи", characterList);
        }

        root.put("lists", lists);

        CommentsList commentsList = new CommentsList(DevPostgressConnection.getInstance());
        commentsList.addComments(root, UUID.fromString(req.getParameter("id")));
        root.put("type", "anime");

        Template tmpl = FreemarkerConfig.getInstance().getTemplate("entity.ftl");
        try {
            tmpl.process(root, resp.getWriter());
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
