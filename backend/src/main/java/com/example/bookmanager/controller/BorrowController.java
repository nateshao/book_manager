package com.example.bookmanager.controller;

import com.example.bookmanager.dto.BorrowRecordDTO;
import com.example.bookmanager.entity.BorrowRecord;
import com.example.bookmanager.service.BorrowService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/borrows")
@RequiredArgsConstructor
public class BorrowController {
    private final BorrowService borrowService;

    @PostMapping("/borrow")
    public BorrowRecordDTO borrowBook(@RequestBody BorrowRequest request) {
        BorrowRecord record = borrowService.borrowBook(request.getUserId(), request.getBookId());
        return toDTO(record);
    }

    @PostMapping("/return/{id}")
    public BorrowRecordDTO returnBook(@PathVariable Long id) {
        BorrowRecord record = borrowService.returnBook(id);
        return toDTO(record);
    }

    @GetMapping("/user/{userId}")
    public List<BorrowRecordDTO> getBorrowRecordsByUser(@PathVariable Long userId) {
        return borrowService.getBorrowRecordsByUser(userId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping
    public List<BorrowRecordDTO> getAllBorrowRecords() {
        return borrowService.getAllBorrowRecords().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private BorrowRecordDTO toDTO(BorrowRecord record) {
        BorrowRecordDTO dto = new BorrowRecordDTO();
        dto.setId(record.getId());
        dto.setUserId(record.getUser().getId());
        dto.setUsername(record.getUser().getUsername());
        dto.setBookId(record.getBook().getId());
        dto.setBookTitle(record.getBook().getTitle());
        dto.setBorrowDate(record.getBorrowDate());
        dto.setReturnDate(record.getReturnDate());
        dto.setStatus(record.getStatus());
        return dto;
    }

    @Data
    public static class BorrowRequest {
        private Long userId;
        private Long bookId;
    }
} 