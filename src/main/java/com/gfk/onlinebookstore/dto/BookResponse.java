package com.gfk.onlinebookstore.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {
    private Long id;

    private String title;

    private Double price;

    private Long stockQuantity;

    private String category;

    private Boolean inStock;

}
