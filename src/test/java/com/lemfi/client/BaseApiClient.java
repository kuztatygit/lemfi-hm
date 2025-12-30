package com.lemfi.client;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import com.lemfi.config.TestConfig;
import com.lemfi.helper.SessionManager;

@Slf4j
public abstract class BaseApiClient {

    static {
        RestAssured.baseURI = TestConfig.getBaseUrl();
    }
    protected RequestSpecification getBaseRequest() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .cookies(SessionManager.getCookies());
    }

    protected Response post(String endpoint, Object body) {
        log.info("POST {} with body: {}", endpoint, body);

        Response response = getBaseRequest()
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();

        SessionManager.storeCookies(response.getDetailedCookies());
        log.info("Response status: {}", response.getStatusCode());

        return response;
    }

    protected Response post(String endpoint) {
        log.info("POST {} without body", endpoint);

        Response response = getBaseRequest()
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();

        SessionManager.storeCookies(response.getDetailedCookies());
        log.info("Response status: {}", response.getStatusCode());

        return response;
    }

    protected Response get(String endpoint) {
        log.info("GET {}", endpoint);

        Response response = getBaseRequest()
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();

        SessionManager.storeCookies(response.getDetailedCookies());
        log.info("Response status: {}", response.getStatusCode());

        return response;
    }
}
