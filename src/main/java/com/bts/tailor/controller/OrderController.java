package com.bts.tailor.controller;

import com.bts.tailor.model.Order;
import com.bts.tailor.model.OrderRequest;
import com.bts.tailor.service.OrderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    /**
     * A customer should be able to see the list of their orders, optionally filtering by order status (e.g., PENDING, ACCEPTED).
     */

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
         this.orderService = orderService;
    }

    /**
     * Retrieve orders for a specific customer.
     * You may choose to retrieve the customerId from the security context in a real application.
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getCustomerOrders(
            @PathVariable Long customerId,
            @RequestParam(value = "status", required = false) String status) {

        //Ensure you implement a method in your OrderService called getOrdersForCustomer(Long customerId, String status) that performs the filtering based on the provided parameters.
        List<Order> orders = orderService.getOrdersForCustomer(customerId, status);
        return ResponseEntity.ok(orders);
    }


    /**
     * Create a new order.
     * Expects multipart/form-data with a JSON part "order" and file part "voiceNote".
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Order> createOrder(
            @RequestPart("order") OrderRequest orderRequest,
            @RequestPart("voiceNote") MultipartFile voiceNote) {
        // The service will handle converting the OrderRequest DTO to an Order entity,
        // process the measurements, and handle the voice note file.
        Order newOrder = orderService.createOrder(orderRequest, voiceNote);
        return ResponseEntity.ok(newOrder);
    }
}
