package com.example.bookmanager.service.impl;

import com.example.bookmanager.entity.BorrowRecord;
import com.example.bookmanager.entity.Book;
import com.example.bookmanager.entity.User;
import com.example.bookmanager.repository.BorrowRecordRepository;
import com.example.bookmanager.repository.BookRepository;
import com.example.bookmanager.repository.UserRepository;
import com.example.bookmanager.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {
    private final BorrowRecordRepository borrowRecordRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public BorrowRecord borrowBook(Long userId, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        if (book.getStock() <= 0) throw new RuntimeException("库存不足");
        User user = userRepository.findById(userId).orElseThrow();
        book.setStock(book.getStock() - 1);
        bookRepository.save(book);
        BorrowRecord record = new BorrowRecord();
        record.setUser(user);
        record.setBook(book);
        record.setBorrowDate(LocalDateTime.now());
        record.setStatus("BORROWED");
        return borrowRecordRepository.save(record);
    }

    @Override
    public BorrowRecord returnBook(Long borrowRecordId) {
        BorrowRecord record = borrowRecordRepository.findById(borrowRecordId).orElseThrow();
        if (!"BORROWED".equals(record.getStatus())) throw new RuntimeException("该记录已归还");
        record.setStatus("RETURNED");
        record.setReturnDate(LocalDateTime.now());
        Book book = record.getBook();
        book.setStock(book.getStock() + 1);
        bookRepository.save(book);
        return borrowRecordRepository.save(record);
    }

    @Override
    public List<BorrowRecord> getBorrowRecordsByUser(Long userId) {
        return borrowRecordRepository.findByUserId(userId);
    }

    @Override
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordRepository.findAll();
    }
} 