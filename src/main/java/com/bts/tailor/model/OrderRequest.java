package com.bts.tailor.model;

import lombok.Data;

@Data
public class OrderRequest {
    private Long customerId;
    private Long timeSlotId;
    private Double bust;
    private Double waist;
    private Double hips;
    private Double height;
}