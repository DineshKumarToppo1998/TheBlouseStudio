package com.tbs.service;

import com.tbs.dto.AvailabilityDto;
import com.tbs.model.Availability;
import com.tbs.model.TimeSlot;
import com.tbs.repository.AvailabilityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;



    public List<TimeSlot> getAvailableTimeSlots(LocalDate date) {
        return availabilityRepository.findByDate(date)
                .map(Availability::getTimeSlots)
                .orElse(Collections.emptyList());
    }

    /**
     * Update availability for a given date && This one deletes the TimeSlot entities that are not present in the updated list
     * but is not able to add new ones
     * @param date
     * @param updatedTimeSlots
     */
    public void updateAvailability_V1(LocalDate date, List<TimeSlot> updatedTimeSlots) {

        // For fetching the availability object from the database by date
        Availability availability = availabilityRepository.findByDate(date)
                .orElseThrow(() -> new EntityNotFoundException("Availability not found for date: " + date));

        // For fetching the existing time slots from the availability object
        List<TimeSlot> existingTimeSlots = availability.getTimeSlots();

        for (TimeSlot updatedTimeSlot : updatedTimeSlots) {
            if(updatedTimeSlot.getId() != null) {
                // For fetching the existing time slot from the availability object
                TimeSlot existingTimeSlot = existingTimeSlots.stream()
                        .filter(ts -> ts.getId().equals(updatedTimeSlot.getId()))
                        .findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("TimeSlot not found with id: " + updatedTimeSlot.getId()));

                if(existingTimeSlot != null) {
                    // Updating the time slot value
                    existingTimeSlot.setTimeSlotValue(updatedTimeSlot.getTimeSlotValue());
                }
            } else {
                // Adding new time slot to the availability object
                updatedTimeSlot.setAvailability(availability);
                existingTimeSlots.add(updatedTimeSlot);
            }
        }

        // Removing the time slots which are not present in the updated time slots
        existingTimeSlots.removeIf(ts -> !updatedTimeSlots.stream().anyMatch(uts -> uts.getId() != null && uts.getId().equals(ts.getId())));

        // Saving the updated availability object
        availabilityRepository.save(availability);
    }

    /**
     * Update availability for a given date & This one does not delete the TimeSlot entities that are not present in the updated list
     * but it updates the existing ones and adds new ones
     * @param date
     * @param updatedTimeSlots
     */
    public void updateAvailability_V2(LocalDate date, List<TimeSlot> updatedTimeSlots) {

        Availability availability = availabilityRepository.findByDate(date)
                .orElseThrow(() -> new EntityNotFoundException("Availability not found for date: " + date));

        List<TimeSlot> existingTimeSlots = availability.getTimeSlots();

        // Loop through updatedTimeSlots and update existing or add new ones
        for (TimeSlot updatedTimeSlot : updatedTimeSlots) {
            if (updatedTimeSlot.getId() != null) {
                TimeSlot existingTimeSlot = existingTimeSlots.stream()
                        .filter(ts -> ts.getId().equals(updatedTimeSlot.getId()))
                        .findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("TimeSlot not found with id: " + updatedTimeSlot.getId()));

                existingTimeSlot.setTimeSlotValue(updatedTimeSlot.getTimeSlotValue());
            } else {
                updatedTimeSlot.setAvailability(availability);
                existingTimeSlots.add(updatedTimeSlot);
            }
        }

        // Save the updated availability object
        availabilityRepository.save(availability);
    }

    /**
     * Update availability for a given date && It deletes the TimeSlot entities that are not present in the updated list
     * @param date
     * @param updatedTimeSlots
     */
    public void updateAvailability_V3(LocalDate date, List<TimeSlot> updatedTimeSlots) {
        Availability availability = availabilityRepository.findByDate(date)
                .orElseThrow(() -> new EntityNotFoundException("Availability not found for date: " + date));

        List<TimeSlot> existingTimeSlots = availability.getTimeSlots();

        // Find time slots to remove (not present in updated list)
        List<TimeSlot> timeSlotsToRemove = existingTimeSlots.stream()
                .filter(existing -> updatedTimeSlots.stream()
                        .noneMatch(updated -> Objects.equals(updated.getId(), existing.getId())))
                .collect(Collectors.toList());

        // Remove from existing time slots
        existingTimeSlots.removeAll(timeSlotsToRemove);

        // Loop through updatedTimeSlots and update existing or add new ones
        for (TimeSlot updatedTimeSlot : updatedTimeSlots) {
            if (updatedTimeSlot.getId() != null) {
                // Update existing time slot
                TimeSlot existingTimeSlot = existingTimeSlots.stream()
                        .filter(ts -> ts.getId().equals(updatedTimeSlot.getId()))
                        .findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("TimeSlot not found with id: " + updatedTimeSlot.getId()));

                existingTimeSlot.setTimeSlotValue(updatedTimeSlot.getTimeSlotValue());
            } else {
                // Add new time slot
                updatedTimeSlot.setAvailability(availability);
                existingTimeSlots.add(updatedTimeSlot);
            }
        }

        // Save the updated availability object (this will automatically delete orphaned TimeSlot entities)
        availabilityRepository.save(availability);
    }







    // Suggested Methods below - not planned for implementation
    public Availability saveAvailability(AvailabilityDto availabilityDto) {
        Availability availability = new Availability();

        // Saving Date into Availability Object
        availability.setDate(availabilityDto.getDate());
        List<TimeSlot> timeSlots = availabilityDto.getTimeSlots().stream()
                .map(timeSlotDto -> new TimeSlot(timeSlotDto.getId(), availability, timeSlotDto.getTimeSlotValue()))
                .collect(Collectors.toList());

        availability.setTimeSlots(timeSlots);
        return availabilityRepository.save(availability);
    }

    public void deleteAvailability(LocalDate date) {
        availabilityRepository.findByDate(date)
                .ifPresent(availabilityRepository::delete);
    }



}
