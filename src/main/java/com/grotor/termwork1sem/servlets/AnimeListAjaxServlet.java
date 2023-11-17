package com.grotor.termwork1sem.servlets;

import com.google.gson.Gson;
import com.grotor.termwork1sem.connection.DevPostgressConnection;
import com.grotor.termwork1sem.dao.AnimeDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class AnimeListAjaxServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter pw = resp.getWriter();

        AnimeDAO animeDAO = new AnimeDAO(DevPostgressConnection.getInstance());
        String body = new Gson().toJson(animeDAO.getAll().stream().map((item) -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id", item.getId().toString());
            hashMap.put("title", item.getTitle());
            return hashMap;
        }).toList());
        pw.print(body);
        pw.flush();
    }
}
