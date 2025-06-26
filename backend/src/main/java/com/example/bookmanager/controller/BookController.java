package com.example.bookmanager.controller;

import com.example.bookmanager.dto.BookDTO;
import com.example.bookmanager.entity.Book;
import com.example.bookmanager.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public BookDTO addBook(@RequestBody Book book) {
        Book saved = bookService.addBook(book);
        return toDTO(saved);
    }

    @PutMapping
    public BookDTO updateBook(@RequestBody Book book) {
        Book updated = bookService.updateBook(book);
        return toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id) {
        return toDTO(bookService.getBookById(id));
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setCategory(book.getCategory());
        dto.setIsbn(book.getIsbn());
        dto.setStock(book.getStock());
        return dto;
    }
} 