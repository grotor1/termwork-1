package com.grotor.termwork1sem.servlets;

import com.grotor.termwork1sem.config.FreemarkerConfig;
import com.grotor.termwork1sem.connection.DevPostgressConnection;
import com.grotor.termwork1sem.dao.CharacterDAO;
import com.grotor.termwork1sem.dao.StudioDAO;
import com.grotor.termwork1sem.entities.Character;
import com.grotor.termwork1sem.entities.Studio;
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

public class StudioServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        StudioDAO studioDAO = new StudioDAO(DevPostgressConnection.getInstance());
        Studio studio = studioDAO.get(UUID.fromString(req.getParameter("id"))).orElse(new Studio());

        Map<String, Object> root = new HashMap<>();

        UserInfo.addUserInfo(req.getSession(), root);

        root.put("pageName", "Студия");

        Map<String, Object> fields = new HashMap<>();

        fields.put("Название", studio.getName());

        fields.put("Описание", studio.getDescription());

        if (!studio.getFoundationDate().equals(DefaultValues.DEFAULT_DATE)) {
            fields.put("Дата основания", studio.getFoundationDate());
        }

        root.put("fields", fields);

        root.put("img", studio.getPhoto());

        Map<String, Map<String, Object>> lists = new HashMap<>();

        if (!studio.getAnime().isEmpty()) {
            Map<String, Object> animeList = new HashMap<>();

            animeList.put("url", "anime");
            animeList.put("list", studio.getAnime().stream().map((item) -> {
                HashMap<String, Object> itemHash = new HashMap<>();
                itemHash.put("id", item.getId());
                itemHash.put("img", item.getPhoto());

                List<String> animeFields = new ArrayList<>();
                animeFields.add(item.getTitle());

                itemHash.put("fields", animeFields);
                return itemHash;
            }).toList());

            lists.put("Аниме", animeList);
        }

        root.put("lists", lists);

        CommentsList commentsList = new CommentsList(DevPostgressConnection.getInstance());
        commentsList.addComments(root, UUID.fromString(req.getParameter("id")));
        root.put("type", "studio");

        Template tmpl = FreemarkerConfig.getInstance().getTemplate("entity.ftl");
        try {
            tmpl.process(root, resp.getWriter());
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
