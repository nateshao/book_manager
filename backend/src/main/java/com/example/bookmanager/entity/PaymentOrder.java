package com.example.bookmanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payment_order")
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String type; // DEPOSIT, FINE, PURCHASE
    private Double amount;
    private String status; // PENDING, PAID, FAILED
    private String wechatTransactionId;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime payTime;
} 