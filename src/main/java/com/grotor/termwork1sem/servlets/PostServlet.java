package com.grotor.termwork1sem.servlets;

import com.grotor.termwork1sem.config.FreemarkerConfig;
import com.grotor.termwork1sem.connection.DevPostgressConnection;
import com.grotor.termwork1sem.dao.PostDAO;
import com.grotor.termwork1sem.dao.StudioDAO;
import com.grotor.termwork1sem.entities.Post;
import com.grotor.termwork1sem.entities.Studio;
import com.grotor.termwork1sem.helpers.DateUtils;
import com.grotor.termwork1sem.services.CommentsList;
import com.grotor.termwork1sem.services.UserInfo;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PostServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> root = new HashMap<>();

        PostDAO postDAO = new PostDAO(DevPostgressConnection.getInstance());
        Post post = postDAO.get(UUID.fromString(req.getParameter("id"))).orElse(new Post());

        UserInfo.addUserInfo(req.getSession(), root);
        CommentsList commentsList = new CommentsList(DevPostgressConnection.getInstance());
        commentsList.addComments(root, UUID.fromString(req.getParameter("id")));

        root.put("date", DateUtils.getFullDate(post.getTime()));
        root.put("title", post.getTitle());
        root.put("text", post.getText());
        root.put("img", post.getPhoto());
        root.put("entityId", post.getId());
        root.put("animeId", post.getAnime().getId());
        root.put("animeTitle", post.getAnime().getTitle());
        root.put("userId", post.getAuthor().getId());
        root.put("username", post.getAuthor().getUsername());

        Template tmpl = FreemarkerConfig.getInstance().getTemplate("post.ftl");
        try {
            tmpl.process(root, resp.getWriter());
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
