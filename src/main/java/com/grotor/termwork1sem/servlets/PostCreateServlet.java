package com.grotor.termwork1sem.servlets;

import com.grotor.termwork1sem.config.FreemarkerConfig;
import com.grotor.termwork1sem.connection.DevPostgressConnection;
import com.grotor.termwork1sem.dao.AccountDAO;
import com.grotor.termwork1sem.dao.AnimeDAO;
import com.grotor.termwork1sem.dao.PostDAO;
import com.grotor.termwork1sem.entities.Account;
import com.grotor.termwork1sem.entities.Anime;
import com.grotor.termwork1sem.entities.AnimeListItem;
import com.grotor.termwork1sem.entities.Post;
import com.grotor.termwork1sem.enums.AnimeListItemType;
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
public class PostCreateServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> root = new HashMap<>();

        UserInfo.addUserInfo(req.getSession(), root);

        Template tmpl = FreemarkerConfig.getInstance().getTemplate("editPost.ftl");
        try {
            tmpl.process(root, resp.getWriter());
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String title = new BufferedReader(new InputStreamReader(req.getPart("title").getInputStream()))
                .lines().collect(Collectors.joining("\n"));
        String text = new BufferedReader(new InputStreamReader(req.getPart("text").getInputStream()))
                .lines().collect(Collectors.joining("\n"));
        String animeId = new BufferedReader(new InputStreamReader(req.getPart("animeId").getInputStream()))
                .lines().collect(Collectors.joining("\n"));

        System.out.println(req.getPart("photo"));
        String filename = MultipartUtils.getFilename(req.getPart("photo"));
        FileUtils.saveFile(filename, req.getPart("photo").getInputStream().readAllBytes());

        AnimeDAO animeDAO = new AnimeDAO(DevPostgressConnection.getInstance());
        PostDAO postDAO = new PostDAO(DevPostgressConnection.getInstance());

        Post post = new Post();

        post.setTitle(title);
        post.setText(text);
        post.setPhoto(filename);
        post.setAnime(animeDAO.get(UUID.fromString(animeId)).orElse(new Anime()));
        post.setAuthor((Account) req.getSession().getAttribute("account"));

        postDAO.save(post);

        resp.sendRedirect("posts");
    }
}
