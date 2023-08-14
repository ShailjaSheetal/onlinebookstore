package com.gfk.onlinebookstore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Value
public class UserRequest {
    @NotNull
    private String name;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Set<String> roleNames = new HashSet<>();


}
