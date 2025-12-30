package com.lemfi.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {

    @JsonProperty("user")
    private User user;

    @JsonProperty("message")
    private Message message;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("email")
        private String email;

        @JsonProperty("firstName")
        private String firstName;

        @JsonProperty("surname")
        private String surname;

        @JsonProperty("personalId")
        private Long personalId;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {
        @JsonProperty("status")
        private String status;

        @JsonProperty("message")
        private String message;
    }
}
