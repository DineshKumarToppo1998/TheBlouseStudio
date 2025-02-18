package com.bts.tailor.repo;

import com.bts.tailor.model.SlotStatus;
import com.bts.tailor.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    // Additional query methods if needed

    List<TimeSlot> findByStatus(SlotStatus status);

    List<TimeSlot> findByStartTimeBetweenAndStatus(LocalDateTime start, LocalDateTime end, SlotStatus status);
}