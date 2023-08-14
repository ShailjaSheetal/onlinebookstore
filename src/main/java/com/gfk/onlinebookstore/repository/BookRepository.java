package com.gfk.onlinebookstore.repository;

import com.gfk.onlinebookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(Long bookId);

    List<Book> findAllByCategory(String name);

    Optional<Book> findByIdAndInStock(Long bookId, Boolean instock);
}