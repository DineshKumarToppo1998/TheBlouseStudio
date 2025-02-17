package com.bts.tailor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStats {
    private int pendingOrders;
    private int acceptedOrders;
    private int rejectedOrders;
}
