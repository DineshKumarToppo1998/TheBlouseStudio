package com.bts.tailor.repo;

import com.bts.tailor.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    // Additional query methods if needed
}