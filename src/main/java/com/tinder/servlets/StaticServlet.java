package com.tinder.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.tinder.utils.Dirs.TEMPLATE_DIR;

public class StaticServlet extends HttpServlet {
    private final String subPath;

    public StaticServlet(String subPath) {
        this.subPath = subPath;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String filename = req.getPathInfo();
        Path path = Paths.get(TEMPLATE_DIR, subPath, filename);

        try (OutputStream os = resp.getOutputStream()) {
            Files.copy(path, os);
        }
    }
}
