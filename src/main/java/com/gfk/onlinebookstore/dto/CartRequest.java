package com.gfk.onlinebookstore.dto;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class CartRequest {

    private List<Long> bookList = new ArrayList<>();
}
