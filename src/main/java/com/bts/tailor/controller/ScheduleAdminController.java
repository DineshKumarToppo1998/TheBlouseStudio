package com.bts.tailor.controller;

import com.bts.tailor.model.TimeSlot;
import com.bts.tailor.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule/admin")
public class ScheduleAdminController {
    
    private final ScheduleService scheduleService;

    public ScheduleAdminController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
    
    @GetMapping
    public ResponseEntity<List<TimeSlot>> getAllTimeSlots() {
        List<TimeSlot> slots = scheduleService.getAllTimeSlots();
        return ResponseEntity.ok(slots);
    }

    @PostMapping
    public ResponseEntity<TimeSlot> createTimeSlot(@RequestBody TimeSlot timeSlot) {
        TimeSlot createdSlot = scheduleService.createTimeSlot(timeSlot);
        return ResponseEntity.ok(createdSlot);
    }

    @PutMapping("/{timeSlotId}")
    public ResponseEntity<TimeSlot> updateTimeSlot(
            @PathVariable Long timeSlotId,
            @RequestBody TimeSlot timeSlot) {
        TimeSlot updatedSlot = scheduleService.updateTimeSlot(timeSlotId, timeSlot);
        return ResponseEntity.ok(updatedSlot);
    }

    @DeleteMapping("/{timeSlotId}")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Long timeSlotId) {
        scheduleService.deleteTimeSlot(timeSlotId);
        return ResponseEntity.noContent().build();
    }


}
