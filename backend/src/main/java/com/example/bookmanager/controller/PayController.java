package com.example.bookmanager.controller;

import com.example.bookmanager.entity.PaymentOrder;
import com.example.bookmanager.service.PaymentOrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pay")
@RequiredArgsConstructor
public class PayController {
    private final PaymentOrderService paymentOrderService;

    @PostMapping("/order")
    public Map<String, Object> createOrder(@RequestBody OrderRequest req) {
        // 实际应获取当前登录用户ID
        Long userId = req.getUserId();
        PaymentOrder order = paymentOrderService.createOrder(userId, req.getType(), req.getAmount(), req.getDescription());
        // 这里用模拟二维码URL，实际应调用微信支付API
        String codeUrl = "https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=wepay://pay/mock/" + order.getId();
        Map<String, Object> result = new HashMap<>();
        result.put("orderId", order.getId());
        result.put("codeUrl", codeUrl);
        return result;
    }

    @GetMapping("/status")
    public Map<String, Object> getOrderStatus(@RequestParam Long id) {
        PaymentOrder order = paymentOrderService.getOrder(id);
        Map<String, Object> result = new HashMap<>();
        result.put("status", order.getStatus());
        return result;
    }

    // 微信支付回调（伪代码）
    @PostMapping("/notify")
    public String wechatNotify(@RequestBody Map<String, Object> notifyData) {
        // 实际应校验签名、解析微信回调数据
        Long orderId = Long.valueOf(notifyData.get("orderId").toString());
        String transactionId = notifyData.get("transactionId").toString();
        paymentOrderService.updateOrderStatus(orderId, "PAID", transactionId);
        return "success";
    }

    @Data
    public static class OrderRequest {
        private Long userId;
        private String type;
        private Double amount;
        private String description;
    }
} 