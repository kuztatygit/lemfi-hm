package java.lemfi.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    @JsonProperty("transactionType")
    private String transactionType;

    @JsonProperty("amount")
    private Amount amount;

    @JsonProperty("accountHolderFullName")
    private String accountHolderFullName;

    @JsonProperty("accountHolderPersonalId")
    private String accountHolderPersonalId;

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("investorId")
    private String investorId;

    @JsonProperty("bookingDate")
    private LocalDate bookingDate;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Amount {
        @JsonProperty("amount")
        private BigDecimal amount;

        @JsonProperty("currency")
        private String currency;
    }

    public static PaymentRequest random() {
        Faker faker = new Faker();
        return PaymentRequest.builder()
                .transactionType("FUNDING")
                .amount(Amount.builder()
                        .amount(new BigDecimal("100.00"))
                        .currency("EUR")
                        .build())
                .accountHolderFullName(faker.name().fullName())
                .accountHolderPersonalId(String.valueOf(faker.number().numberBetween(100000000L, 999999999L)))
                .accountNumber("GB29NWBK60161331926819")
                .investorId("1")
                .bookingDate(LocalDate.now())
                .build();
    }

    public static PaymentRequest withAmount(BigDecimal amount, String currency) {
        PaymentRequest request = random();
        request.setAmount(Amount.builder()
                .amount(amount)
                .currency(currency)
                .build());
        return request;
    }
}
