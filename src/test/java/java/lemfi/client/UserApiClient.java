package java.lemfi.client;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.lemfi.model.request.PersonalDataRequest;
import java.lemfi.model.response.UserResponse;

@Slf4j
public class UserApiClient extends BaseApiClient {

    private static final String PERSONAL_DATA_ENDPOINT = "/api/personal-data";

    public UserResponse updatePersonalData(PersonalDataRequest request) {
        log.info("Updating personal data: {} {}", request.getFirstName(), request.getSurname());

        Response response = post(PERSONAL_DATA_ENDPOINT, request);

        if (response.getStatusCode() != 200 && response.getStatusCode() != 201) {
            log.error("Personal data update failed with status {}: {}",
                    response.getStatusCode(), response.getBody().asString());
            throw new RuntimeException("Personal data update failed: " + response.getBody().asString());
        }

        UserResponse userResponse = response.as(UserResponse.class);
        log.info("Personal data updated successfully: {} {}",
                userResponse.getUser().getFirstName(), userResponse.getUser().getSurname());

        return userResponse;
    }

    public Response updatePersonalDataWithoutBody() {
        log.info("Attempting to update personal data without body");
        return post(PERSONAL_DATA_ENDPOINT);
    }

    public Response updatePersonalDataRaw(PersonalDataRequest request) {
        log.info("Update personal data with raw response");
        return post(PERSONAL_DATA_ENDPOINT, request);
    }
}