package com.gfk.onlinebookstore.repository;

import com.gfk.onlinebookstore.entity.CartBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartBookRepository extends JpaRepository<CartBook, Long> {
    Optional<CartBook> findByCartIdAndBookId(Long cartId,Long bookId);
}
