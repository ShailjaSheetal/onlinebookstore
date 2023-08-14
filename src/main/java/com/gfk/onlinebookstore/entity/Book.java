package com.gfk.onlinebookstore.entity;

import com.gfk.onlinebookstore.dto.BookRequest;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Double price;

    private Long stockQuantity;

    private String category;

    private Boolean inStock;

    public Book(BookRequest bookRequest) {
        this.title = bookRequest.getTitle();
        this.category = bookRequest.getCategory();
        this.price = bookRequest.getPrice();
        this.stockQuantity = bookRequest.getStockQuantity();
    }
}
