package com.tinder;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class HerokuEnv {
    private static final String PORT = "PORT";
    private static final String EMPTY = " - IS EMPTY!";
    private static final String JDBC_DATABASE_URL = "JDBC_DATABASE_URL";
    private static final String JDBC_DATABASE_USERNAME = "JDBC_DATABASE_USERNAME";
    private static final String JDBC_DATABASE_PASSWORD = "JDBC_DATABASE_PASSWORD";

    public static int port() {
        try {
            return Integer.parseInt(System.getenv(PORT));
        } catch (NumberFormatException ex) {
            log.error(String.format("Issue arisen while parsing environmental variable: %s", PORT));
            return 5000;
        }
    }

    public static String jdbc_url() {
        String url = System.getenv(JDBC_DATABASE_URL);
        if (url == null) {
          log.error(String.format("Issue arisen while getting environmental variable: %s", JDBC_DATABASE_URL));
          throw new IllegalArgumentException(JDBC_DATABASE_URL + EMPTY);
        }
        return url;
    }

    public static String jdbc_username() {
        String url = System.getenv(JDBC_DATABASE_USERNAME);
        if (url == null) {
          log.error(String.format("Issue arisen while getting environmental variable: %s", JDBC_DATABASE_USERNAME));
          throw new IllegalArgumentException(JDBC_DATABASE_USERNAME + EMPTY);
        }
        return url;
    }

    public static String jdbc_password() {
        String url = System.getenv(JDBC_DATABASE_PASSWORD);
        if (url == null) {
          log.error(String.format("Issue arisen while getting environmental variable: %s", JDBC_DATABASE_PASSWORD));
          throw new IllegalArgumentException(JDBC_DATABASE_PASSWORD + EMPTY);
        }
        return url;
    }
}
