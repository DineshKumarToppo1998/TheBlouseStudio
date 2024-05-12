package com.tbs.controller;

import com.tbs.dto.AvailabilityDto;
import com.tbs.dto.TimeSlotDto;
import com.tbs.model.Availability;
import com.tbs.model.TimeSlot;
import com.tbs.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tbs/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;


    @GetMapping("/{date}")
    public ResponseEntity<AvailabilityDto> getAvailableTimeSlots(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TimeSlot> timeSlots = availabilityService.getAvailableTimeSlots(date);
        AvailabilityDto availabilityDto = new AvailabilityDto(null, date, timeSlots.stream()
                .map(this::convertToTimeSlotDto)
                .collect(Collectors.toList()));
        return ResponseEntity.ok(availabilityDto);
    }


    @PutMapping("/{date}")
    public ResponseEntity<Void> updateAvailability(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,@RequestBody List<TimeSlot> timeSlots){
        availabilityService.updateAvailability(date, timeSlots);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save")
    public ResponseEntity<AvailabilityDto> saveAvailability(@RequestBody AvailabilityDto availabilityDto) {
        Availability savedAvailability = availabilityService.saveAvailability(availabilityDto);
        AvailabilityDto savedAvailabilityDto = convertAvailabilityToDto(savedAvailability);
        return ResponseEntity.ok(savedAvailabilityDto);
    }

    private AvailabilityDto convertAvailabilityToDto(Availability availability) {
        List<TimeSlotDto> timeSlotDtos = availability.getTimeSlots().stream()
                .map(timeSlot -> new TimeSlotDto(timeSlot.getId(), timeSlot.getTimeSlotValue()))
                .collect(Collectors.toList());

        return new AvailabilityDto(availability.getId(), availability.getDate(), timeSlotDtos);
    }

    private TimeSlotDto convertToTimeSlotDto(TimeSlot timeSlot) {
        return new TimeSlotDto(timeSlot.getId(), timeSlot.getTimeSlotValue());
    }

}
