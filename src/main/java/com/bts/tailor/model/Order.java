package com.bts.tailor.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    // Assuming you have a Customer entity; otherwise, store the ID
    private Long customerId;

    // An embedded class to hold measurement details
    @Embedded
    private Measurements measurements;

    // URL or file path for the uploaded voice note
    private String voiceNoteUrl;

    // Reference to the selected time slot (you can later add a relation if needed)
    private Long timeSlotId;

    // Order status: PENDING, ACCEPTED, REJECTED, or COMPLETED
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // Timestamps for record-keeping
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Order(Long customerId, Measurements measurements, String voiceNoteUrl, Long timeSlotId, OrderStatus orderStatus) {
        this.customerId = customerId;
        this.measurements = measurements;
        this.voiceNoteUrl = voiceNoteUrl;
        this.timeSlotId = timeSlotId;
        this.status = orderStatus;
    }
}
