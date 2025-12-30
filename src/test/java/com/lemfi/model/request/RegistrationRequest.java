package com.lemfi.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.datafaker.Faker;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
    public static RegistrationRequest random() {
        Faker faker = new Faker();
        return RegistrationRequest.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password(8, 20, true, true, true))
                .build();
    }
}
