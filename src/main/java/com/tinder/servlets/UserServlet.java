package com.tinder.servlets;

import com.tinder.utils.EncoderDecoder;
import lombok.extern.log4j.Log4j2;
import com.tinder.dao.DAOLike;
import com.tinder.dao.DAOUser;
import com.tinder.entities.User;
import com.tinder.services.UsersService;
import com.tinder.utils.TemplateEngine;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.tinder.utils.Converters.strToInt;
import static com.tinder.utils.GetReqData.getCookie;
import static com.tinder.utils.GetReqData.getCookieValue;

@Log4j2
public class UserServlet extends HttpServlet {
    private final UsersService USERS_SERVICE;
    private final TemplateEngine TEMPLATE_ENGINE;
    private final EncoderDecoder ed = new EncoderDecoder();

    public UserServlet(DAOUser DAO_USER, DAOLike DAO_LIKE, TemplateEngine templateEngine) {
        this.USERS_SERVICE = new UsersService(DAO_USER, DAO_LIKE);
        this.TEMPLATE_ENGINE = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Optional<Cookie> whoCookie = getCookie(req, "logged_user_id");
        int logged_user_id = strToInt(getCookieValue(whoCookie));

        List<User> likedUsers = USERS_SERVICE.getLikedUsers(logged_user_id);

        if (likedUsers.size() != 0) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("listOfLikedUsers", likedUsers);
            TEMPLATE_ENGINE.render("people-list.ftl", hashMap, resp);
        } else {
            try (PrintWriter w = resp.getWriter()) {
                w.write("You have not liked anybody yet");
                log.info("You have not liked anybody yet");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        String [] testArr = action.split("To");

        if ("sendMessage".equals(testArr[0])) {
            try {
                Optional<Cookie> whomCookie = getCookie(req, "liked_user_id");
                whomCookie.ifPresent(e -> e.setValue(ed.encrypt(testArr[1])));
                whomCookie.ifPresent(e -> resp.addCookie(whomCookie.get()));
                int liked_user_id = strToInt(getCookieValue(whomCookie));
                resp.sendRedirect(String.format("/message/%d", liked_user_id));
            } catch (Exception e) {
                log.error("Exception caught! Redirected to /users...");
                resp.sendRedirect("/users");
            }
        } else {
            Cookie[] cookies = req.getCookies();
            Arrays.stream(cookies)
                    .forEach(c -> {
                        c.setMaxAge(0);
                        resp.addCookie(c);
                    });
            try {
                resp.sendRedirect("/*");
            } catch (IOException e) {
                log.error("Exception caught!!!");
            }
        }
    }
}