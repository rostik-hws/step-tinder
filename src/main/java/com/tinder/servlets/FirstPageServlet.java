package com.tinder.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.tinder.utils.Dirs.TEMPLATE_DIR;

public class FirstPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (OutputStream os = resp.getOutputStream()) {
            Files.copy(Paths.get(TEMPLATE_DIR, "first-page.html"), os);
        }
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String nextPage = "login".equals(req.getParameter("action")) ? "/login" : "/register";
//        resp.sendRedirect(nextPage);
//    }
}