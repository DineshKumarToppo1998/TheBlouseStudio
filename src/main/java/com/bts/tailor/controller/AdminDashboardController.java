package com.bts.tailor.controller;

import com.bts.tailor.model.DashboardStats;
import com.bts.tailor.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard/admin")
public class AdminDashboardController {

    private final OrderService orderService;
    
    public AdminDashboardController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping
    public ResponseEntity<DashboardStats> getDashboardStats() {
        DashboardStats stats = orderService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
}
