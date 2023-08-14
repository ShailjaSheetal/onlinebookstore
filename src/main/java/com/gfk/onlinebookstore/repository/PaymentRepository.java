package com.gfk.onlinebookstore.repository;

import com.gfk.onlinebookstore.entity.Cart;
import com.gfk.onlinebookstore.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
