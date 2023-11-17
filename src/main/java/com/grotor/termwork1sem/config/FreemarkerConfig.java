package com.grotor.termwork1sem.config;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.ServletContext;
import java.util.TimeZone;

public class FreemarkerConfig {
    private static Configuration cfg;
    private static ServletContext sc;

    public static void setSc(ServletContext servletContext) {
        sc = servletContext;
    }

    public static Configuration getInstance () {
        if (cfg == null) {
            init();
        }
        return cfg;
    }


    private static void init () {
        cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setServletContextForTemplateLoading(sc, "/WEB-INF/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
    }
}
