package com.gfk.onlinebookstore.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PaymentResponse {

    private Long id;

    private Date paymentDate;

    private CartResponse cart;

    private Double paidPrice;

    private String paymentMode;
}
