package com.gfk.onlinebookstore.service;

import com.gfk.onlinebookstore.dto.BookRequest;
import com.gfk.onlinebookstore.dto.BookResponse;
import com.gfk.onlinebookstore.entity.Book;
import com.gfk.onlinebookstore.exception.ResourceNotFoundException;
import com.gfk.onlinebookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class BookServiceTest {

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Test
    void should_return_added_book() {
        BookRequest bookRequest = new BookRequest("title",10.50,10L,"Fiction");
        when(bookRepository.save(any(Book.class))).thenReturn(new Book(bookRequest));
        BookResponse bookResponse = bookService.addToInventory(bookRequest);
        assertEquals("title",bookResponse.getTitle());
        assertEquals(10.50,bookResponse.getPrice());
        assertEquals(10L,bookResponse.getStockQuantity());
        assertEquals("Fiction",bookResponse.getCategory());
    }

    @Test
    void removeFromInventory() {
        Book book = new Book();
        Mockito.when(bookRepository.findById(10L).
                orElseThrow(() -> new ResourceNotFoundException("Book ", "id",10L))).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.findByIdAndInStock(10L,Boolean.TRUE)
        .orElseThrow(() -> new ResourceNotFoundException("Book ", "id",10L))).thenReturn(book);
        BookResponse bookResponse = bookService.removeFromInventory(10L);
        //TODO
        assertEquals(null,bookResponse);
    }

    @Test
    void should_return_updated_fields() {
        BookRequest bookRequest = new BookRequest("title",10.50,10L,"Fiction");
        Mockito.when(bookRepository.findById(10L).orElseThrow(() -> new ResourceNotFoundException("Book ", "id",10L))).thenReturn(any(Book.class));
        when(bookRepository.save(any(Book.class))).thenReturn(new Book(bookRequest));
        BookResponse bookResponse = bookService.addToInventory(bookRequest);
        assertEquals("Fiction",bookResponse.getCategory());
    }

    @Test
    void should_return_all_by_category() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(new BookRequest("title1",10.50,10L,"Fiction")));
        bookList.add(new Book(new BookRequest("title2",20.50,10L,"THRILLER")));
        bookList.add(new Book(new BookRequest("title3",10.50,10L,"Fiction")));
        when(bookRepository.findAllByCategory("Fiction")).thenReturn(bookList);
        assertEquals(2,bookService.getAllByCategory("Fiction").size());
        assertEquals(1,bookService.getAllByCategory("THRILLER").size());
    }
}