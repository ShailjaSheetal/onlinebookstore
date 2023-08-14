package com.gfk.onlinebookstore.dto;

import com.gfk.onlinebookstore.entity.CartBook;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CartResponse {
    private Long id;
    private Set<CartBook> books;
    private Double totalPrice;


}
