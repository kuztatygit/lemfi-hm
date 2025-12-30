package com.lemfi.apitests;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.lemfi.client.AuthApiClient;
import com.lemfi.client.PaymentApiClient;
import com.lemfi.client.UserApiClient;
import com.lemfi.helper.SessionManager;

@Slf4j
public abstract class BaseTest {

    protected AuthApiClient authClient;
    protected UserApiClient userClient;
    protected PaymentApiClient paymentClient;

    @BeforeEach
    void setUp() {
        log.info("Test Setup");

        authClient = new AuthApiClient();
        userClient = new UserApiClient();
        paymentClient = new PaymentApiClient();

        SessionManager.clearCookies();
        log.info("API clients initialized");
    }

    @AfterEach
    void tearDown() {
        log.info("Test Teardown");
        SessionManager.clearCookies();
        log.info("Test cleanup completed");
    }
}
