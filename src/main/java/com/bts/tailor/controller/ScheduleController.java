package com.bts.tailor.controller;

import com.bts.tailor.model.TimeSlot;
import com.bts.tailor.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * Retrieve available time slots.
     * Optional query parameter "date" (format: yyyy-MM-dd) to filter slots.
     */
    @GetMapping
    public ResponseEntity<List<TimeSlot>> getAvailableTimeSlots(
            @RequestParam(value = "date", required = false) String date) {
        List<TimeSlot> slots = scheduleService.getAvailableTimeSlots(date);
        return ResponseEntity.ok(slots);
    }

    /**
     * Retrieve all time slots with their availability status.
     * This allows customers to see when the admin is available.
     */
    @GetMapping("/all")
    public ResponseEntity<List<TimeSlot>> getAllTimeSlots() {
        List<TimeSlot> slots = scheduleService.getAllTimeSlots();
        return ResponseEntity.ok(slots);
    }

    /**
     * Check if the admin is currently available.
     * This endpoint returns a simple boolean indicating admin availability.
     */
    @GetMapping("/admin-availability")
    public ResponseEntity<Boolean> isAdminAvailable() {
        // For now, we'll consider the admin available if there are any available time slots
        List<TimeSlot> availableSlots = scheduleService.getAvailableTimeSlots(null);
        boolean isAdminAvailable = !availableSlots.isEmpty();
        return ResponseEntity.ok(isAdminAvailable);
    }
}

