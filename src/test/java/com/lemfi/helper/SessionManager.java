package com.lemfi.helper;

import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionManager {

    private static final ThreadLocal<Cookies> sessionCookies = ThreadLocal.withInitial(Cookies::new);

    public static void storeCookies(Cookies cookies) {
        if (cookies != null && !cookies.asList().isEmpty()) {
            sessionCookies.set(cookies);
            Cookie jsessionid = cookies.get("JSESSIONID");
            if (jsessionid != null) {
                log.info("JSESSIONID cookie stored: {}", jsessionid.getValue());
            }
        }
    }

    public static Cookies getCookies() {
        return sessionCookies.get();
    }

    public static void clearCookies() {
        log.info("Clearing session cookies");
        sessionCookies.remove();
    }

    public static boolean hasSession() {
        Cookies cookies = sessionCookies.get();
        return cookies != null && cookies.get("JSESSIONID") != null;
    }

    public static String getSessionId() {
        Cookies cookies = sessionCookies.get();
        if (cookies != null) {
            Cookie jsessionid = cookies.get("JSESSIONID");
            if (jsessionid != null) {
                return jsessionid.getValue();
            }
        }
        return null;
    }
}
