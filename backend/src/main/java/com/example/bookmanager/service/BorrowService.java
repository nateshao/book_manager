package com.example.bookmanager.service;

import com.example.bookmanager.entity.BorrowRecord;
import java.util.List;

public interface BorrowService {
    BorrowRecord borrowBook(Long userId, Long bookId);
    BorrowRecord returnBook(Long borrowRecordId);
    List<BorrowRecord> getBorrowRecordsByUser(Long userId);
    List<BorrowRecord> getAllBorrowRecords();
} 