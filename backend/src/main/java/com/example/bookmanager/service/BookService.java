package com.example.bookmanager.service;

import com.example.bookmanager.entity.Book;
import java.util.List;

public interface BookService {
    Book addBook(Book book);
    Book updateBook(Book book);
    void deleteBook(Long id);
    Book getBookById(Long id);
    List<Book> getAllBooks();
} 