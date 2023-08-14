package com.gfk.onlinebookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gfk.onlinebookstore.dto.PaymentRequest;
import com.gfk.onlinebookstore.enums.PaymentMode;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date paymentDate;

    @PrePersist
    protected void onCreate() {
        paymentDate = new Date();
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    @JsonIgnore
    private Cart cart;

    private Double paidPrice;

    private String paymentMode;

    public Payment (PaymentRequest paymentRequest){
        this.cart.setId( paymentRequest.getCartId());
        this.paymentMode = paymentRequest.getPaymentMode().name();
    }
}
