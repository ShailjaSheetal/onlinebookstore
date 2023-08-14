package com.gfk.onlinebookstore.dto;

import com.gfk.onlinebookstore.enums.PaymentMode;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class PaymentRequest {
    private Long cartId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;


}
