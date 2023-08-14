package com.gfk.onlinebookstore.controller;

import com.gfk.onlinebookstore.dto.CartRequest;
import com.gfk.onlinebookstore.dto.CartResponse;
import com.gfk.onlinebookstore.dto.PaymentRequest;
import com.gfk.onlinebookstore.dto.PaymentResponse;
import com.gfk.onlinebookstore.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/add")
    public ResponseEntity<CartResponse> add(@Valid @RequestBody CartRequest cartRequest){
        return new ResponseEntity<>(cartService.addToCart(cartRequest), HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/remove")
    public ResponseEntity<CartResponse> remove(@Valid @RequestBody CartRequest cartRequest){
        return new ResponseEntity<>(cartService.removeFromCart(cartRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/checkout/{cartId}")
    public ResponseEntity<CartResponse> checkout(@PathVariable Long cartId){
        return new ResponseEntity<>(cartService.checkoutCart(cartId), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/payment")
    public ResponseEntity<PaymentResponse> pay(@Valid @RequestBody PaymentRequest paymentRequest){
        return new ResponseEntity<>(cartService.payForCart(paymentRequest), HttpStatus.CREATED);
    }

}
