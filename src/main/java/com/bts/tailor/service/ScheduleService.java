package com.bts.tailor.service;

import com.bts.tailor.model.SlotStatus;
import com.bts.tailor.model.TimeSlot;
import com.bts.tailor.repo.TimeSlotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    private final TimeSlotRepository timeSlotRepository;

    public ScheduleService(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    /**
     * Retrieve all time slots.
     * 
     * @return List of TimeSlot objects
     */
    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotRepository.findAll();
    }

    /**
     * Create a new time slot.
     * 
     * @param timeSlot the TimeSlot object to create
     * @return the created TimeSlot
     */
    public TimeSlot createTimeSlot(TimeSlot timeSlot) {
        return timeSlotRepository.save(timeSlot);
    }

    /**
     * Update an existing time slot.
     * 
     * @param timeSlotId the identifier of the time slot to update
     * @param updatedTimeSlot the TimeSlot object containing updated details
     * @return the updated TimeSlot
     */
    public TimeSlot updateTimeSlot(Long timeSlotId, TimeSlot updatedTimeSlot) {
        return timeSlotRepository.findById(timeSlotId)
                .map(slot -> {
                    slot.setStartTime(updatedTimeSlot.getStartTime());
                    slot.setEndTime(updatedTimeSlot.getEndTime());
                    slot.setStatus(updatedTimeSlot.getStatus());
                    return timeSlotRepository.save(slot);
                })
                .orElseThrow(() -> new RuntimeException("TimeSlot not found"));
    }

    /**
     * Delete a time slot by its ID.
     * 
     * @param timeSlotId the identifier of the time slot to delete
     */
    public void deleteTimeSlot(Long timeSlotId) {
        timeSlotRepository.deleteById(timeSlotId);
    }

    /**
     * Retrieves available time slots.
     * If a date is provided (format: yyyy-MM-dd), filters time slots for that day.
     * Otherwise, returns all available time slots.
     *
     * @param date the date in format yyyy-MM-dd (optional)
     * @return List of available TimeSlot objects.
     */
    public List<TimeSlot> getAvailableTimeSlots(String date) {
        if (date == null || date.trim().isEmpty()) {
            // Return all available time slots if no date is provided.
            return timeSlotRepository.findByStatus(SlotStatus.AVAILABLE);
        }
        // Parse the date and create start and end of day bounds.
        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.plusDays(1).atStartOfDay();

        return timeSlotRepository.findByStartTimeBetweenAndStatus(startOfDay, endOfDay, SlotStatus.AVAILABLE);
    }
}
