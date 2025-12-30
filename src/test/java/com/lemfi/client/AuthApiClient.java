package com.lemfi.client;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import com.lemfi.model.request.RegistrationRequest;
import com.lemfi.model.response.UserResponse;

@Slf4j
public class AuthApiClient extends BaseApiClient {

    private static final String SIGN_UP_ENDPOINT = "/public/sign-up";

    public UserResponse signUp(RegistrationRequest request) {
        log.info("Registering user: {}", request.getEmail());

        Response response = post(SIGN_UP_ENDPOINT, request);

        if (response.getStatusCode() != 200) {
            log.error("Registration failed with status {}: {}",
                    response.getStatusCode(), response.getBody().asString());
            throw new RuntimeException("Registration failed: " + response.getBody().asString());
        }

        UserResponse userResponse = response.as(UserResponse.class);
        log.info("User registered successfully: ID={}, Email={}",
                userResponse.getUser().getId(), userResponse.getUser().getEmail());

        return userResponse;
    }
}
