package com.gfk.onlinebookstore.service;

import com.gfk.onlinebookstore.dto.BookRequest;
import com.gfk.onlinebookstore.dto.BookResponse;
import com.gfk.onlinebookstore.entity.Book;
import com.gfk.onlinebookstore.exception.ResourceNotFoundException;
import com.gfk.onlinebookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse addToInventory(BookRequest bookRequest) {
        Book book = bookRepository.save(new Book(bookRequest));
        return getBookResponse(book);
    }

    public BookResponse removeFromInventory(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book ", "id",bookId));
        book.setInStock(Boolean.FALSE);
        bookRepository.save(book);
        book= bookRepository.findByIdAndInStock(bookId,Boolean.TRUE)
                .orElseThrow(() -> new ResourceNotFoundException("Book ", "id",bookId));
        return getBookResponse(book);
    }

    public BookResponse updateDetails(Long bookId,BookRequest bookRequest) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book ", "id",bookId));
        book.setStockQuantity(bookRequest.getStockQuantity());
        book.setCategory(bookRequest.getCategory());
        book.setPrice(bookRequest.getPrice());
        book.setTitle(bookRequest.getTitle());
        bookRepository.save(book);
        return getBookResponse(book);
    }

    public List<BookResponse>  getAllByCategory(String name) {
        List<BookResponse> bookResponseList= new ArrayList<>();

        bookRepository.findAllByCategory(name).forEach(book->
                    bookResponseList.add(getBookResponse(book)));

        return bookResponseList;
    }

    private BookResponse getBookResponse(Book book){
        return BookResponse.builder().id(book.getId()).category(book.getCategory()).price(book.getPrice()).stockQuantity(book.getStockQuantity()).title(book.getTitle()).build();
    }
}
