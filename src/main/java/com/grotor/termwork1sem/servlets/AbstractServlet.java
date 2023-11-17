package com.grotor.termwork1sem.servlets;

import com.grotor.termwork1sem.config.FreemarkerConfig;

import javax.servlet.http.HttpServlet;

public abstract class AbstractServlet extends HttpServlet {
    @Override
    public void init() {
        FreemarkerConfig.setSc(this.getServletContext());
    }
}
