package com.gfk.onlinebookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    private Long userId;

   @OneToMany(mappedBy="cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartBook> books;


    private Double totalPrice;

    @OneToOne(mappedBy = "cart")
    @JsonIgnore
    private Payment payment;

    public Cart(User user) {
        this.userId = user.getId();
    }
}
