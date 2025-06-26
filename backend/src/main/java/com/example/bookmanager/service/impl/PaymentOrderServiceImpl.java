package com.example.bookmanager.service.impl;

import com.example.bookmanager.entity.PaymentOrder;
import com.example.bookmanager.repository.PaymentOrderRepository;
import com.example.bookmanager.service.PaymentOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentOrderServiceImpl implements PaymentOrderService {
    private final PaymentOrderRepository paymentOrderRepository;

    @Override
    public PaymentOrder createOrder(Long userId, String type, Double amount, String description) {
        PaymentOrder order = new PaymentOrder();
        order.setUserId(userId);
        order.setType(type);
        order.setAmount(amount);
        order.setStatus("PENDING");
        order.setDescription(description);
        order.setCreateTime(LocalDateTime.now());
        return paymentOrderRepository.save(order);
    }

    @Override
    public PaymentOrder updateOrderStatus(Long id, String status, String wechatTransactionId) {
        PaymentOrder order = paymentOrderRepository.findById(id).orElseThrow();
        order.setStatus(status);
        order.setWechatTransactionId(wechatTransactionId);
        if ("PAID".equals(status)) {
            order.setPayTime(LocalDateTime.now());
        }
        return paymentOrderRepository.save(order);
    }

    @Override
    public PaymentOrder getOrder(Long id) {
        return paymentOrderRepository.findById(id).orElseThrow();
    }
} 