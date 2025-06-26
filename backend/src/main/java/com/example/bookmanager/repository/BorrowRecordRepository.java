package com.example.bookmanager.repository;

import com.example.bookmanager.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByUserId(Long userId);
} 