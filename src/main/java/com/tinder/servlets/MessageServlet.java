package com.tinder.servlets;

import com.tinder.dao.DAOMessage;
import com.tinder.dao.DAOUser;
import com.tinder.entities.Message;
import com.tinder.entities.User;
import com.tinder.services.MessageService;
import com.tinder.utils.TemplateEngine;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.tinder.utils.Converters.strToInt;
import static com.tinder.utils.GetReqData.getCookie;
import static com.tinder.utils.GetReqData.getCookieValue;

public class MessageServlet extends HttpServlet {
    private final MessageService MESSAGE_SERVICE;
    private final TemplateEngine TEMPLATE_ENGINE;

    public MessageServlet(DAOUser DAO_USER, DAOMessage DAO_MESSAGE, TemplateEngine templateEngine) {
        this.MESSAGE_SERVICE = new MessageService(DAO_USER, DAO_MESSAGE);
        this.TEMPLATE_ENGINE = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Optional<Cookie> whoCookie = getCookie(req, "logged_user_id");
        Optional<Cookie> whomCookie = getCookie(req, "liked_user_id");

        int logged_user_id = strToInt(getCookieValue(whoCookie));
        int liked_user_id = strToInt(getCookieValue(whomCookie));

        List<Message> messages = MESSAGE_SERVICE.getMessages(logged_user_id, liked_user_id);

        User receiver = MESSAGE_SERVICE.getUserInfo(liked_user_id);

        HashMap<String, Object> data = new HashMap<>();

        data.put("receiver_name", receiver.getUsername());
        data.put("receiver_photo_url", receiver.getPhoto_url());
        data.put("messages", messages);
        data.put("receiver_id", receiver.getId());

        TEMPLATE_ENGINE.render("chat.ftl", data, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Optional<Cookie> whoCookie = getCookie(req, "logged_user_id");
        Optional<Cookie> whomCookie = getCookie(req, "liked_user_id");

        int from_id = strToInt(getCookieValue(whoCookie));
        int to_id = strToInt(getCookieValue(whomCookie));

        String message = req.getParameter("message");
        MESSAGE_SERVICE.addMessage(from_id, to_id, message, LocalDate.now());

        resp.sendRedirect(String.format("/message/%d", to_id));
    }
}
