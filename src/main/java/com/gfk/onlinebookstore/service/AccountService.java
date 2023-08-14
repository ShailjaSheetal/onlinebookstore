package com.gfk.onlinebookstore.service;

import com.gfk.onlinebookstore.dto.LoginRequest;
import com.gfk.onlinebookstore.dto.UserRequest;
import com.gfk.onlinebookstore.dto.UserResponse;
import com.gfk.onlinebookstore.entity.User;
import com.gfk.onlinebookstore.entity.Role;
import com.gfk.onlinebookstore.exception.APIException;
import com.gfk.onlinebookstore.repository.RoleRepository;
import com.gfk.onlinebookstore.repository.UserRepository;
import com.gfk.onlinebookstore.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AccountService {
    private AuthenticationManager authenticationManager;
        private UserRepository userRepository;
        private RoleRepository roleRepository;
        private PasswordEncoder passwordEncoder;
        private JwtTokenProvider jwtTokenProvider;


        public AccountService(AuthenticationManager authenticationManager,
                              UserRepository userRepository,
                              RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder,
                              JwtTokenProvider jwtTokenProvider) {
            this.authenticationManager = authenticationManager;
            this.userRepository = userRepository;
            this.roleRepository = roleRepository;
            this.passwordEncoder = passwordEncoder;
            this.jwtTokenProvider = jwtTokenProvider;
        }

        public String login(LoginRequest loginRequest) {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.generateToken(authentication);

            return token;
        }

        public UserResponse register(UserRequest userRequest) {

            if(userRepository.existsByUsername(userRequest.getUsername())){
                throw new APIException(HttpStatus.BAD_REQUEST, "Username already exists!.");
            }

            // add check for email exists in database
            if(userRepository.existsByEmail(userRequest.getEmail())){
                throw new APIException(HttpStatus.BAD_REQUEST, "Email already exists!.");
            }

            User user = new User(userRequest);
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

            Set<Role> roles  = roleRepository.findByNameIn(userRequest.getRoleNames());
            user.setRoles(roles);

            userRepository.save(user);

            return getUserResponse(user);
        }

    private UserResponse getUserResponse(User user) {
           return UserResponse.builder().id(user.getId()).email(user.getEmail()).name(user.getName()).roles(user.getRoles()).build();
    }
}

