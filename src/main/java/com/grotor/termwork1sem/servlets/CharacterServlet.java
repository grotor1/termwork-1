package com.grotor.termwork1sem.servlets;

import com.grotor.termwork1sem.config.FreemarkerConfig;
import com.grotor.termwork1sem.connection.DevPostgressConnection;
import com.grotor.termwork1sem.dao.CharacterDAO;
import com.grotor.termwork1sem.entities.Character;
import com.grotor.termwork1sem.services.CommentsList;
import com.grotor.termwork1sem.services.UserInfo;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class CharacterServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        CharacterDAO characterDAO = new CharacterDAO(DevPostgressConnection.getInstance());
        Character character = characterDAO.get(UUID.fromString(req.getParameter("id"))).orElse(new Character());

        Map<String, Object> root = new HashMap<>();

        UserInfo.addUserInfo(req.getSession(), root);

        root.put("pageName", "Персонаж");

        Map<String, Object> fields = new HashMap<>();

        fields.put("Имя", character.getName());

        fields.put("Описание", character.getDescription());

        root.put("fields", fields);

        root.put("img", character.getPhoto());

        Map<String, Map<String, Object>> lists = new HashMap<>();

        if (!character.getAnime().isEmpty()) {
            Map<String, Object> animeList = new HashMap<>();

            animeList.put("url", "anime");
            animeList.put("list", character.getAnime().stream().map((item) -> {
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

        if (!character.getVAs().isEmpty()) {
            Map<String, Object> vasList = new HashMap<>();

            vasList.put("url", "person");
            vasList.put("list", character.getVAs().stream().map((item) -> {
                HashMap<String, Object> itemHash = new HashMap<>();
                itemHash.put("id", item.getPerson().getId());
                itemHash.put("img", item.getPerson().getPhoto());

                List<String> personField = new ArrayList<>();
                personField.add(item.getPerson().getName());
                personField.add(item.getVaType().text);

                itemHash.put("fields", personField);
                return itemHash;
            }).toList());

            lists.put("Сейю", vasList);
        }

        root.put("lists", lists);

        CommentsList commentsList = new CommentsList(DevPostgressConnection.getInstance());
        commentsList.addComments(root, UUID.fromString(req.getParameter("id")));
        root.put("type", "character");

        Template tmpl = FreemarkerConfig.getInstance().getTemplate("entity.ftl");
        try {
            tmpl.process(root, resp.getWriter());
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
