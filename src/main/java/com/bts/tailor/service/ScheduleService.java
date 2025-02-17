package com.bts.tailor.service;

import com.bts.tailor.model.TimeSlot;
import com.bts.tailor.repo.TimeSlotRepository;
import org.springframework.stereotype.Service;

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
}
