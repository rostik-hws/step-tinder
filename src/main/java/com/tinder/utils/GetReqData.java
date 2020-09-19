package com.tinder.utils;

import lombok.extern.log4j.Log4j2;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@Log4j2
public class GetReqData {
    private static final EncoderDecoder ed = new EncoderDecoder();

    public static Optional<Cookie> getCookie(HttpServletRequest req, String cookieName) {
        return Arrays.stream(req.getCookies())
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .findFirst();
    }

    public static String getCookieValue(Optional<Cookie> cookie) {
        if (cookie.isPresent()) {
            return ed.decrypt(cookie.get().getValue());
        } else {
            log.error("Cookie is empty!!!");
            return null;
        }
    }
}