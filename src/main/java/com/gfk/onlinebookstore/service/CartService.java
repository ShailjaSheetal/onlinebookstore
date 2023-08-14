package com.gfk.onlinebookstore.service;

import com.gfk.onlinebookstore.dto.CartRequest;
import com.gfk.onlinebookstore.dto.CartResponse;
import com.gfk.onlinebookstore.dto.PaymentRequest;
import com.gfk.onlinebookstore.dto.PaymentResponse;
import com.gfk.onlinebookstore.entity.Book;
import com.gfk.onlinebookstore.entity.Cart;
import com.gfk.onlinebookstore.entity.CartBook;
import com.gfk.onlinebookstore.entity.Payment;
import com.gfk.onlinebookstore.exception.ResourceNotFoundException;
import com.gfk.onlinebookstore.repository.BookRepository;
import com.gfk.onlinebookstore.repository.CartBookRepository;
import com.gfk.onlinebookstore.repository.CartRepository;
import com.gfk.onlinebookstore.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService{

    private CustomUserDetailsService customUserDetailsService;
    private CartRepository cartRepository;
    private BookRepository bookRepository;
    private CartBookRepository cartBookRepository;
    private PaymentRepository paymentRepository;

    private Cart cart = new Cart();


    public CartService( BookRepository bookRepository,CartRepository cartRepository, CartBookRepository cartBookRepository,CustomUserDetailsService customUserDetailsService,PaymentRepository paymentRepository) {
        this.bookRepository = bookRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.cartRepository = cartRepository;
        this.cartBookRepository = cartBookRepository;
        this.paymentRepository = paymentRepository;
    }

    public CartResponse addToCart(CartRequest cartRequest) {
        Optional<Cart> optionalCart = findCartForUser();
        if (optionalCart.isEmpty())
            createCartWithBooks(cartRequest);
       else
            updateCartWithNewBooks(cart.getId(),cartRequest);
       return getCartResponse(cart);
    }

    private CartResponse getCartResponse(Cart cart){
       return CartResponse.builder().id(cart.getId()).books(cart.getBooks()).totalPrice(cart.getTotalPrice()).build();
}

    private PaymentResponse getPaymentResponse(Payment payment){
        return PaymentResponse.builder().id(payment.getId()).cart(
                getCartResponse(payment.getCart()))
                .paidPrice(payment.getPaidPrice()).paymentMode(payment.getPaymentMode()).paymentDate(payment.getPaymentDate()).build();
    }

    private Optional<Cart> findCartForUser(){
       return cartRepository.findByUserId(customUserDetailsService.getUser().getId());
    }

    private Cart updateCartWithNewBooks(Long cartId,CartRequest cartRequest) {
        for (Long bookId: cartRequest.getBookList()){
            Optional<CartBook> optionalCartBook = cartBookRepository.findByCartIdAndBookId(cartId,bookId);
            optionalCartBook.ifPresentOrElse(
                    (value) -> {
                        value.setQuantity(value.getQuantity()+1);
                        cartBookRepository.save(value);
                        },
                    () -> cartBookRepository.save(new CartBook(bookId,cartId,1L))
            );
        }
        return cart;
    }

    public CartResponse removeFromCart(CartRequest cartRequest) {
        cart = findCartForUser()
                .orElseThrow(() -> new ResourceNotFoundException("Cart ", "userId",customUserDetailsService.getUser().getId()));

        for (Long bookId: cartRequest.getBookList()){
                CartBook cartBook = cartBookRepository.findByCartIdAndBookId(cart.getId(),bookId).get();
                cartBook.setQuantity(cartBook.getQuantity()-1);
                cartBookRepository.save(cartBook);
            }
        return getCartResponse(cart);
    }

    public CartResponse checkoutCart(Long cartId) {
        double totalPrice = 0.0;
        cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart ", "cartId",cartId));
        for (CartBook cartBook: cart.getBooks()){
            totalPrice+=cartBook.getQuantity()*cartBook.getBook().getPrice();
        }
       cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
        return getCartResponse(cart);
    }


    private Cart createCartWithBooks(CartRequest cartRequest){
        cart = new Cart(customUserDetailsService.getUser());
        cartRepository.save(cart);
        for (Long bookId: cartRequest.getBookList())
            cartBookRepository.save(new CartBook(bookId,cart.getId(),1L));
        return cart;
    }


    public PaymentResponse payForCart(PaymentRequest paymentRequest) {
        cart = cartRepository.findById(paymentRequest.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart ", "cartId",paymentRequest.getCartId()));;
        Payment payment = new Payment(paymentRequest);
        payment.setPaidPrice(cart.getTotalPrice());
        paymentRepository.save(payment);
        updateInventory(cart);
        return getPaymentResponse(payment);
    }

    //Update the stock after payment is made
    private void updateInventory(Cart cart) {
        for(CartBook cartBook:cart.getBooks()){
            Book book = cartBook.getBook();
            book.setStockQuantity(book.getStockQuantity()-cartBook.getQuantity());
            bookRepository.save(book);
        }

    }
}
