package com.tinder.utils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Log4j2
public class TemplateEngine {

    final private Configuration conf;

    public TemplateEngine(String fullPath) throws IOException {
        this.conf = new Configuration(Configuration.VERSION_2_3_28) {{
            setDirectoryForTemplateLoading(new File(fullPath));
            setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
            setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            setLogTemplateExceptions(false);
            setWrapUncheckedExceptions(true);
        }};
    }

    public static TemplateEngine folder(String path) throws IOException {
        return new TemplateEngine(path);
    }

    public void render(String template, HashMap<String, Object> data, HttpServletResponse resp) {
        resp.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));

        try (PrintWriter w = resp.getWriter()) {
            conf.getTemplate(template).process(data, w);
        } catch (TemplateException | IOException e) {
            log.error("Freemarker ERROR");
            System.out.println(e);
        }
    }
}