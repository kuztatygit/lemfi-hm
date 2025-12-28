package java.lemfi.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Payment response model from API
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("rawResponse")
    private String rawResponse;
}
