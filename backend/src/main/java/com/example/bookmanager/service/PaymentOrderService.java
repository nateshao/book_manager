package com.example.bookmanager.service;

import com.example.bookmanager.entity.PaymentOrder;

public interface PaymentOrderService {
    PaymentOrder createOrder(Long userId, String type, Double amount, String description);
    PaymentOrder updateOrderStatus(Long id, String status, String wechatTransactionId);
    PaymentOrder getOrder(Long id);
} 