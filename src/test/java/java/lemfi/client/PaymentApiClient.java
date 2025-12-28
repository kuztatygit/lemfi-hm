package java.lemfi.client;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.lemfi.model.request.PaymentRequest;
import java.lemfi.model.response.PaymentResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class PaymentApiClient extends BaseApiClient {

    private static final String ADD_FUNDS_ENDPOINT = "/api/add-funds";
    private static final String BALANCE_ENDPOINT = "/api/balance";
    private static final String PAYMENTS_ENDPOINT = "/api/payments";

    public Long addFunds(PaymentRequest request) {
        log.info("Adding funds: {} {}", request.getAmount().getAmount(), request.getAmount().getCurrency());

        Response response = post(ADD_FUNDS_ENDPOINT, request);

        if (response.getStatusCode() != 200) {
            log.error("Add funds failed with status {}: {}",
                    response.getStatusCode(), response.getBody().asString());
            throw new RuntimeException("Add funds failed: " + response.getBody().asString());
        }

        String responseText = response.getBody().asString();
        Long paymentId = Long.parseLong(responseText.replaceAll(".*id: (\\d+).*", "$1"));
        log.info("Funds added successfully: Payment ID={}", paymentId);

        return paymentId;
    }

    public BigDecimal getBalance() {
        log.info("Getting account balance");

        Response response = get(BALANCE_ENDPOINT);

        if (response.getStatusCode() != 200) {
            log.error("Get balance failed with status {}: {}",
                    response.getStatusCode(), response.getBody().asString());
            throw new RuntimeException("Get balance failed: " + response.getBody().asString());
        }

        BigDecimal balance = new BigDecimal(response.getBody().asString());
        log.info("Current balance: {}", balance);

        return balance;
    }

    public List<PaymentResponse> getPayments() {
        log.info("Getting payment history");

        Response response = get(PAYMENTS_ENDPOINT);

        if (response.getStatusCode() != 200) {
            log.error("Get payments failed with status {}: {}",
                    response.getStatusCode(), response.getBody().asString());
            throw new RuntimeException("Get payments failed: " + response.getBody().asString());
        }

        PaymentResponse[] payments = response.as(PaymentResponse[].class);
        List<PaymentResponse> paymentList = payments != null ? Arrays.asList(payments) : Collections.emptyList();
        log.info("Retrieved {} payments", paymentList.size());

        return paymentList;
    }

    public Response addFundsRaw(PaymentRequest request) {
        log.info("Add funds with raw response");
        return post(ADD_FUNDS_ENDPOINT, request);
    }

    public Response getBalanceRaw() {
        log.info("Get balance with raw response");
        return get(BALANCE_ENDPOINT);
    }

    public Response getPaymentsRaw() {
        log.info("Get payments with raw response");
        return get(PAYMENTS_ENDPOINT);
    }
}