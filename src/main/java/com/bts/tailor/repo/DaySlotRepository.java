package com.bts.tailor.repo;

import com.bts.tailor.model.DaySlot;
import com.bts.tailor.model.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DaySlotRepository extends JpaRepository<DaySlot, Long> {

    /**
     * To find filter by Admin Status
     * @param status
     * @return
     */
    List<DaySlot> findByStatus(SlotStatus status);

    /**
     * To save the Dates admin will be available (access to the admin only)
     * @param daySlots
     * @return
     */
    List<DaySlot> saveAndFlush(List<DaySlot> daySlots);

    List<DaySlot> findAll();


    List<DaySlot> findByDateBetweenAndStatus(LocalDate today, LocalDate futureDate, SlotStatus slotStatus);
}
