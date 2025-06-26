package com.example.bookmanager.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BorrowRecordDTO {
    private Long id;
    private Long userId;
    private String username;
    private Long bookId;
    private String bookTitle;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private String status;
} 