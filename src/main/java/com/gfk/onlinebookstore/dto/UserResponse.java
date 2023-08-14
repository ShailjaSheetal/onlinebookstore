package com.gfk.onlinebookstore.dto;

import com.gfk.onlinebookstore.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserResponse {
    private Long id;

    private String name;

    private String username;

    private String email;

    private String password;

    private Set<Role> roles;

}
