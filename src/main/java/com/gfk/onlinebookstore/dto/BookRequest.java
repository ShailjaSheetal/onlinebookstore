package com.gfk.onlinebookstore.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class BookRequest {

    @NonNull
    private String title;

    @NonNull
    private Double price;

    private Long stockQuantity;

    private String category;
}
