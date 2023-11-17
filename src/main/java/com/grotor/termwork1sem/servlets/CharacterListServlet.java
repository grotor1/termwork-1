package com.grotor.termwork1sem.servlets;

import com.grotor.termwork1sem.config.FreemarkerConfig;
import com.grotor.termwork1sem.connection.DevPostgressConnection;
import com.grotor.termwork1sem.services.*;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterListServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> root = new HashMap<>();

        UserInfo.addUserInfo(req.getSession(), root);

        int page = PageGetter.getPage(req);
        String query = QueryGetter.getQuery(req);

        CharactersList characters = new CharactersList(DevPostgressConnection.getInstance());
        List<HashMap<String, String>> lsp;
        int pageCount;

        if (query != null) {
            pageCount = characters.getPageCount(query);
            lsp = characters.getCharacterPage(page, query);
        } else {
            pageCount = characters.getPageCount();
            lsp = characters.getCharacterPage(page);
        }

        root.put("curPage", page);

        root.put("query", query);

        root.put("pageCount", pageCount);

        root.put("characters", lsp.toArray());

        Template tmpl = FreemarkerConfig.getInstance().getTemplate("characters.ftl");
        try {
            tmpl.process(root, resp.getWriter());
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}