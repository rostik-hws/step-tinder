package com.tinder.filters;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CookieFilter implements Filter {
    private boolean isHttp(ServletRequest req) {
        return req instanceof HttpServletRequest;
    }

    public boolean isCookieOk(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return false;
        for (Cookie c : cookies) {
            if ("logged_user_id".equals(c.getName()) && c.getValue() != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isHttp(request) && isCookieOk((HttpServletRequest) request)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {

    }
}
