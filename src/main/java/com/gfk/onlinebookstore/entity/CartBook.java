package com.gfk.onlinebookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    @JsonIgnore
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    @JsonIgnore
    private Cart cart;

    private Long quantity;

    public CartBook (Long bookId,Long cartId, Long quantity){
        this.cart.setId( cartId);
        this.book.setId(bookId);
        this.quantity = quantity;
    }
}
