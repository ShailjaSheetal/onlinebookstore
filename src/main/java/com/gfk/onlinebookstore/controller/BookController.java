package com.gfk.onlinebookstore.controller;

import com.gfk.onlinebookstore.dto.*;
import com.gfk.onlinebookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<BookResponse> add(@Valid @RequestBody BookRequest bookRequest){
        return new ResponseEntity<>(bookService.addToInventory(bookRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{bookId}")
    public ResponseEntity<BookResponse> delete(@PathVariable Long bookId){
        return new ResponseEntity<>(bookService.removeFromInventory(bookId),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{bookId}")
    public ResponseEntity<BookResponse> update(@PathVariable Long bookId,@Valid @RequestBody BookRequest bookRequest){
        return new ResponseEntity<>(bookService.updateDetails(bookId,bookRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/category/{name}")
    public ResponseEntity<List<BookResponse>> getAllByCategory(@PathVariable String name){
        return new ResponseEntity<>(bookService.getAllByCategory(name), HttpStatus.CREATED);
    }

}
