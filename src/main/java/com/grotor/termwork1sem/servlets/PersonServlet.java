package com.grotor.termwork1sem.servlets;

import com.grotor.termwork1sem.config.FreemarkerConfig;
import com.grotor.termwork1sem.connection.DevPostgressConnection;
import com.grotor.termwork1sem.dao.PersonDAO;
import com.grotor.termwork1sem.entities.Person;
import com.grotor.termwork1sem.helpers.DateUtils;
import com.grotor.termwork1sem.services.CommentsList;
import com.grotor.termwork1sem.services.UserInfo;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class PersonServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        PersonDAO personDAO = new PersonDAO(DevPostgressConnection.getInstance());
        Person person = personDAO.get(UUID.fromString(req.getParameter("id"))).orElse(new Person());

        Map<String, Object> root = new HashMap<>();

        UserInfo.addUserInfo(req.getSession(), root);

        root.put("pageName", "Персона");

        Map<String, String> fields = new HashMap<>();

        fields.put("Имя", person.getName());
        fields.put("Биография", person.getDescription());
        fields.put("Дата рождения", DateUtils.getFullDate(person.getBirthdate()));

        root.put("fields", fields);

        root.put("img", person.getPhoto());

        Map<String, Map<String, Object>> lists = new HashMap<>();

        if (!person.getWorks().isEmpty()) {
            Map<String, Object> worksList = new HashMap<>();

            worksList.put("url", "anime");
            worksList.put("list", person.getWorks().stream().map((item) -> {
                HashMap<String, Object> itemHash = new HashMap<>();
                itemHash.put("id", item.getAnime().getId());
                itemHash.put("img", item.getAnime().getPhoto());

                List<String> animeFields = new ArrayList<>();
                animeFields.add(item.getAnime().getTitle());
                animeFields.add(item.getStaffType().text);

                itemHash.put("fields", animeFields);
                return itemHash;
            }).toList());

            lists.put("Работы", worksList);
        }

        if (!person.getVoicing().isEmpty()) {
            Map<String, Object> vaList = new HashMap<>();

            vaList.put("url", "character");
            vaList.put("list", person.getVoicing().stream().map((item) -> {
                HashMap<String, Object> itemHash = new HashMap<>();
                itemHash.put("id", item.getCharacter().getId());
                itemHash.put("img", item.getCharacter().getPhoto());

                List<String> characterFields = new ArrayList<>();
                characterFields.add(item.getCharacter().getName());
                characterFields.add(item.getVaType().text);

                itemHash.put("fields", characterFields);
                return itemHash;
            }).toList());

            lists.put("Озвучка", vaList);
        }

        root.put("lists", lists);

        CommentsList commentsList = new CommentsList(DevPostgressConnection.getInstance());
        commentsList.addComments(root, UUID.fromString(req.getParameter("id")));
        root.put("type", "person");

        Template tmpl = FreemarkerConfig.getInstance().getTemplate("entity.ftl");
        try {
            tmpl.process(root, resp.getWriter());
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
