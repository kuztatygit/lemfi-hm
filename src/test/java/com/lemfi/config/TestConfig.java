package com.lemfi.config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestConfig {

    private static final String BASE_URL = System.getProperty("api.base.url", "http://127.0.0.1:8080");
    private static final int TIMEOUT = Integer.parseInt(System.getProperty("api.timeout", "30000"));

    public static String getBaseUrl() {
        log.info("Using API base URL: {}", BASE_URL);
        return BASE_URL;
    }

    public static int getTimeout() {
        return TIMEOUT;
    }

    public static String getEndpoint(String path) {
        return BASE_URL + path;
    }
}
