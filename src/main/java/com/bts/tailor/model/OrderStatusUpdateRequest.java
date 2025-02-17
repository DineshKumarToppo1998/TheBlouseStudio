package com.bts.tailor.model;

import lombok.Data;

@Data
public class OrderStatusUpdateRequest {
    private String status;
    private String adminNote;
}