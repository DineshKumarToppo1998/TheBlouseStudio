package com.bts.tailor.controller;

import com.bts.tailor.model.Order;
import com.bts.tailor.model.OrderStatusUpdateRequest;
import com.bts.tailor.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders/admin")
public class OrderAdminController {

    private final OrderService orderService;
    
    public OrderAdminController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(
            @RequestParam(value = "status", required = false) String status) {
        List<Order> orders = orderService.getAllOrders(status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusUpdateRequest request) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, request.getStatus(), request.getAdminNote());
        return ResponseEntity.ok(updatedOrder);
    }

}
