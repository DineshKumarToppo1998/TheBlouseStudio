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
        // You can enhance the ScheduleService to filter by date if required.

        //In your ScheduleService, you may implement getAvailableTimeSlots(String date) to either ignore or filter based on the date parameter.
        List<TimeSlot> slots = scheduleService.getAvailableTimeSlots(date);
        return ResponseEntity.ok(slots);
    }
}
