package org.cinema.events.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class PaymentEventRequest {
    @JsonProperty("payment_id")
    private Long paymentId;

    @JsonProperty("user_id")
    private Long userId;

    private BigDecimal amount;

    private String status;

    private Instant timestamp;

    @JsonProperty("method_type")
    private String methodType;
}
