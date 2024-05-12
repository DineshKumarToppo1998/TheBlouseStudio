package com.tbs.service;

import com.tbs.dto.AvailabilityDto;
import com.tbs.model.Availability;
import com.tbs.model.TimeSlot;
import com.tbs.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
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

    public void updateAvailability(LocalDate date, List<TimeSlot> timeSlots) {
        Availability availability = availabilityRepository.findByDate(date)
                .orElse(new Availability(date, timeSlots));
        //                .orElseThrow(() -> new IllegalArgumentException("Availability not found for date: " + date));
        availability.setTimeSlots(timeSlots);
        availabilityRepository.save(availability);

    }


    // Suggested Methods below - not planned for implementation
    public Availability saveAvailability(AvailabilityDto availabilityDto) {
        Availability availability = new Availability();
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
