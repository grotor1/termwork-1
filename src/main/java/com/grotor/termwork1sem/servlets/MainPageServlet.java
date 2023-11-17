package com.grotor.termwork1sem.servlets;

import com.grotor.termwork1sem.config.FreemarkerConfig;
import com.grotor.termwork1sem.connection.DevPostgressConnection;
import com.grotor.termwork1sem.services.RecentAnime;
import com.grotor.termwork1sem.services.RecentPosts;
import com.grotor.termwork1sem.services.UserInfo;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPageServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> root = new HashMap<>();

        UserInfo.addUserInfo(req.getSession(), root);

        RecentAnime anime = new RecentAnime(DevPostgressConnection.getInstance());
        List<HashMap<String, String>> lsa = anime.getAnime();

        root.put("recentAnime", lsa.toArray());

        RecentPosts posts = new RecentPosts(DevPostgressConnection.getInstance());
        List<HashMap<String, String>> lsp = posts.getPosts();

        root.put("recentPost", lsp.toArray());

        Template tmpl = FreemarkerConfig.getInstance().getTemplate("index.ftl");
        try {
            tmpl.process(root, resp.getWriter());
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
