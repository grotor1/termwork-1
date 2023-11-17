package com.grotor.termwork1sem.servlets;

import com.google.gson.Gson;
import com.grotor.termwork1sem.entities.Account;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class UserAnimeListAjaxServlet extends AbstractServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter pw = resp.getWriter();

        Account account = (Account) req.getSession().getAttribute("account");
        String body = new Gson().toJson(account.getAnimeList().stream().map((item) -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id", item.getAnime().getId().toString());
            hashMap.put("title", item.getAnime().getTitle());
            hashMap.put("type", item.getType().text);
            return hashMap;
        }).toList());
        pw.print(body);
        pw.flush();
    }
}
