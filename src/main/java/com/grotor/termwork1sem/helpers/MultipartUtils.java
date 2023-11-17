package com.grotor.termwork1sem.helpers;

import javax.servlet.http.Part;

public class MultipartUtils {
    public static String getFilename(Part part) {
        for (String entry : part.getHeader("content-disposition").split(";")) {
            if (entry.trim().startsWith("filename")) {
                return entry.substring(entry.indexOf("=") + 2, entry.length()-1);
            }
        }
        return "";
    }
}
