package java.lemfi.tests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lemfi.helper.SessionManager;
import java.lemfi.model.request.PaymentRequest;
import java.lemfi.model.request.PersonalDataRequest;
import java.lemfi.model.request.RegistrationRequest;
import java.lemfi.model.response.PaymentResponse;
import java.lemfi.model.response.UserResponse;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Tag("e2e")
@DisplayName("User Journey E2E Tests")
public class UserJourneyE2ETest extends BaseTest {

    @Test
    @DisplayName("Complete user journey: registration → profile update → top-up → balance verification → payment history")
    void completeUserJourneyTest() {
        log.info(" Starting E2E User Journey Test");

        log.info("STEP 1: User Registration");
        RegistrationRequest registrationRequest = RegistrationRequest.random();
        log.info("Registering user: {}", registrationRequest.getEmail());

        UserResponse registrationResponse = authClient.signUp(registrationRequest);

        assertNotNull(registrationResponse, "Registration response should not be null");
        assertNotNull(registrationResponse.getUser(), "User object should not be null");
        assertNotNull(registrationResponse.getUser().getId(), "User ID should be generated");
        assertEquals(registrationRequest.getEmail(), registrationResponse.getUser().getEmail(),
                "Email should match registration request");
        assertEquals("SUCCESS", registrationResponse.getMessage().getStatus(),
                "Registration should be successful");

        assertTrue(SessionManager.hasSession(), "JSESSIONID cookie should be stored");
        String sessionId = SessionManager.getSessionId();
        assertNotNull(sessionId, "Session ID should not be null");
        log.info("User registered successfully (ID: {})", registrationResponse.getUser().getId());
        log.info("JSESSIONID extracted: {}...", sessionId.substring(0, 8));

        log.info("STEP 2: Personal Data Update");
        PersonalDataRequest personalDataRequest = PersonalDataRequest.random();
        log.info("Updating personal data: {} {}",
                personalDataRequest.getFirstName(), personalDataRequest.getSurname());

        UserResponse personalDataResponse = userClient.updatePersonalData(personalDataRequest);

        assertNotNull(personalDataResponse, "Personal data response should not be null");
        assertNotNull(personalDataResponse.getUser(), "User object should not be null");
        assertEquals(personalDataRequest.getFirstName(), personalDataResponse.getUser().getFirstName(),
                "First name should be updated");
        assertEquals(personalDataRequest.getSurname(), personalDataResponse.getUser().getSurname(),
                "Surname should be updated");
        assertEquals(personalDataRequest.getPersonalId(), personalDataResponse.getUser().getPersonalId(),
                "Personal ID should be updated");
        log.info("✓ Personal data updated successfully: {} {}",
                personalDataResponse.getUser().getFirstName(),
                personalDataResponse.getUser().getSurname());


        log.info("\n STEP 3: Balance Top-Up ");
        BigDecimal topUpAmount = new BigDecimal("25.50");
        String currency = "EUR";

        PaymentRequest paymentRequest = PaymentRequest.withAmount(topUpAmount, currency);
        log.info("Adding funds: {} {}", topUpAmount, currency);

        Long paymentId = paymentClient.addFunds(paymentRequest);

        assertNotNull(paymentId, "Payment ID should be returned");
        assertTrue(paymentId > 0, "Payment ID should be positive");
        log.info("✓ Funds added successfully (Payment ID: {})", paymentId);

        log.info("\n=== STEP 4: Balance Verification ===");
        BigDecimal actualBalance = paymentClient.getBalance();

        assertNotNull(actualBalance, "Balance should not be null");
        assertEquals(0, topUpAmount.compareTo(actualBalance),
                String.format("Balance should equal top-up amount. Expected: %s, Actual: %s",
                        topUpAmount, actualBalance));
        log.info("✓ Balance verified: {} {} (matches top-up amount)", actualBalance, currency);

        log.info("\n=== STEP 5: Payment History Verification ===");
        List<PaymentResponse> payments = paymentClient.getPayments();

        assertNotNull(payments, "Payments list should not be null");
        assertFalse(payments.isEmpty(), "Payments list should not be empty");

        boolean foundPayment = payments.stream()
                .anyMatch(p -> p.getId().equals(paymentId));
        assertTrue(foundPayment, "Payment history should contain the top-up transaction");

        PaymentResponse topUpPayment = payments.stream()
                .filter(p -> p.getId().equals(paymentId))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Top-up payment not found in history"));

        assertEquals(0, topUpAmount.compareTo(topUpPayment.getAmount()),
                "Payment amount in history should match top-up amount");
        assertEquals("FUNDING", topUpPayment.getType(),
                "Payment type should be FUNDING");
        log.info("✓ Payment history verified: {} payments, top-up transaction confirmed",
                payments.size());
    }
}
