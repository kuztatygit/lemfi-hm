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
public class PersonalDataRequest {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("personalId")
    private Long personalId;

    public static PersonalDataRequest random() {
        Faker faker = new Faker();
        return PersonalDataRequest.builder()
                .firstName(faker.name().firstName())
                .surname(faker.name().lastName())
                .personalId(faker.number().numberBetween(100000000L, 999999999L))
                .build();
    }
}
