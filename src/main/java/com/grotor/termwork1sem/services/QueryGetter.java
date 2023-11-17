package com.grotor.termwork1sem.services;

import javax.servlet.http.HttpServletRequest;

public class QueryGetter {
    public static String getQuery(HttpServletRequest req) {
        return req.getParameter("q");
    }
}
