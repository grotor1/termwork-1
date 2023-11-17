package com.grotor.termwork1sem.services;

import javax.servlet.http.HttpServletRequest;

public class PageGetter {
    public static int getPage (HttpServletRequest req) {
        String pageStr = req.getParameter("page");
        return pageStr == null ? 1 : Integer.parseInt(pageStr);
    }
}
