package com.tinder.servlets;

import lombok.extern.log4j.Log4j2;
import com.tinder.dao.DAOUser;
import com.tinder.entities.User;
import com.tinder.services.LoginService;
import com.tinder.utils.EncoderDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

import static com.tinder.utils.Converters.intToStr;
import static com.tinder.utils.Dirs.TEMPLATE_DIR;

@Log4j2
public class LoginServlet extends HttpServlet {
    private final LoginService LOGIN_SERVICE;
    private final EncoderDecoder ed = new EncoderDecoder();

    public LoginServlet(DAOUser DAO_USER) {
        this.LOGIN_SERVICE = new LoginService(DAO_USER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (OutputStream os = resp.getOutputStream()) {
            Files.copy(Paths.get(TEMPLATE_DIR, "login.html"), os);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Optional<User> isRegisteredUser = LOGIN_SERVICE.isRegisteredUser(username, password);

        if (isRegisteredUser.isPresent()) {
            User user = isRegisteredUser.get();
            LOGIN_SERVICE.updateLastLoginDate(user, LocalDate.now());
            resp.addCookie(new Cookie("logged_user_id", ed.encrypt(intToStr(user.getId()))));
            resp.sendRedirect("/users");
        } else {
            log.error("Something went wrong while login operation");
            resp.sendRedirect("/login");
        }
    }
}
