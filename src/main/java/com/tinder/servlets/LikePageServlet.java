package com.tinder.servlets;

import lombok.extern.log4j.Log4j2;
import com.tinder.dao.DAOLike;
import com.tinder.dao.DAOUser;
import com.tinder.entities.User;
import com.tinder.services.LikePageService;
import com.tinder.utils.EncoderDecoder;
import com.tinder.utils.TemplateEngine;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.tinder.utils.Converters.intToStr;
import static com.tinder.utils.Converters.strToInt;
import static com.tinder.utils.GetReqData.getCookie;
import static com.tinder.utils.GetReqData.getCookieValue;

@Log4j2
public class LikePageServlet extends HttpServlet {
    private final LikePageService LIKE_PAGE_SERVICE;
    private final TemplateEngine TEMPLATE_ENGINE;
    private static List<User> userList;
    private final EncoderDecoder ed = new EncoderDecoder();

    public LikePageServlet(DAOUser DAO_USER, DAOLike DAO_LIKE, TemplateEngine templateEngine) {
        LIKE_PAGE_SERVICE = new LikePageService(DAO_USER, DAO_LIKE);
        this.TEMPLATE_ENGINE = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Optional<Cookie> whoCookie = getCookie(req, "logged_user_id");

        int logged_user_id = strToInt(getCookieValue(whoCookie));

        userList = LIKE_PAGE_SERVICE.getUsersExceptThis(logged_user_id);

        if (userList.size() != 0) {
            User user = userList.get(0);

            HashMap<String, Object> data = new HashMap<>();
            data.put("username", user.getUsername());
            data.put("photoUrl", user.getPhoto_url());

            Cookie whom = new Cookie("liked_user_id", ed.encrypt(intToStr(user.getId())));
            Cookie index = new Cookie("index", ed.encrypt("0"));

            resp.addCookie(whom);
            resp.addCookie(index);
            TEMPLATE_ENGINE.render("like-page.ftl", data, resp);
        } else {
            try (PrintWriter w = resp.getWriter()) {
                w.write("No users to swipe found");
                log.info("No users to swipe found");
            } catch (IOException e) {
                log.error("Exception caught!");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean reaction = "like".equals(req.getParameter("reaction"));

        Optional<Cookie> whoCookie = getCookie(req, "logged_user_id");
        Optional<Cookie> whomCookie = getCookie(req, "liked_user_id");
        Optional<Cookie> idxCookie = getCookie(req, "index");

        int logged_user_id = strToInt(getCookieValue(whoCookie));
        int liked_user_id = strToInt(getCookieValue(whomCookie));
        int index = strToInt(getCookieValue(idxCookie));

        LIKE_PAGE_SERVICE.addReaction(logged_user_id, liked_user_id, reaction);

        if (index != userList.size() - 1) {
            index += 1;
            User nextUser = userList.get(index);

            HashMap<String, Object> data = new HashMap<>();
            data.put("username", nextUser.getUsername());
            data.put("photoUrl", nextUser.getPhoto_url());

            whomCookie.get().setValue(ed.encrypt(intToStr(nextUser.getId())));
            idxCookie.get().setValue(ed.encrypt(intToStr(index)));

            resp.addCookie(whomCookie.get());
            resp.addCookie(idxCookie.get());
            TEMPLATE_ENGINE.render("like-page.ftl", data, resp);
        } else resp.sendRedirect("/likes");
    }
}