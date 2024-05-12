package com.tbs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDto {
    private Long id;
    private LocalDate date;
    private List<TimeSlotDto> timeSlots;
}
