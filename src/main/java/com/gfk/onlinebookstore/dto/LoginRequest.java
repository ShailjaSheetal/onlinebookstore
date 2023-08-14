package com.gfk.onlinebookstore.dto;

import lombok.*;

@Value
public class LoginRequest {
    private String usernameOrEmail;
    private String password;
}
