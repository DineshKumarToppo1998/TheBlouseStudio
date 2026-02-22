package com.bts.tailor.controller;

import com.bts.tailor.model.DashboardStats;
import com.bts.tailor.model.TimeSlot;
import com.bts.tailor.service.OrderService;
import com.bts.tailor.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-view")
public class CustomerViewController {

    private final ScheduleService scheduleService;
    private final OrderService orderService;

    public CustomerViewController(ScheduleService scheduleService, OrderService orderService) {
        this.scheduleService = scheduleService;
        this.orderService = orderService;
    }

    /**
     * Get the current status of the admin (available or not).
     * This endpoint provides a simple boolean indicating if the admin is accepting new orders.
     */
    @GetMapping("/admin-status")
    public ResponseEntity<Boolean> getAdminStatus() {
        DashboardStats stats = orderService.getDashboardStats();
        return ResponseEntity.ok(stats.isAdminAvailable());
    }

    /**
     * Get statistics about the current state of the business.
     * This includes information about pending orders and admin availability.
     */
    @GetMapping("/statistics")
    public ResponseEntity<DashboardStats> getBusinessStatistics() {
        DashboardStats stats = orderService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }

    /**
     * Get all available time slots for booking.
     * This allows customers to see when they can schedule appointments.
     */
    @GetMapping("/available-slots")
    public ResponseEntity<List<TimeSlot>> getAvailableTimeSlots(
            @RequestParam(value = "date", required = false) String date) {
        List<TimeSlot> slots = scheduleService.getAvailableTimeSlots(date);
        return ResponseEntity.ok(slots);
    }

    /**
     * Get all time slots with their current status.
     * This gives customers a complete view of the admin's schedule.
     */
    @GetMapping("/schedule")
    public ResponseEntity<List<TimeSlot>> getAllTimeSlots() {
        List<TimeSlot> slots = scheduleService.getAllTimeSlots();
        return ResponseEntity.ok(slots);
    }
}