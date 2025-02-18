package com.bts.tailor.service;

import com.bts.tailor.model.*;
import com.bts.tailor.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final VoiceNoteService voiceNoteService; // for handling file uploads

    // Constructor Injection
    public OrderService(OrderRepository orderRepository, VoiceNoteService voiceNoteService) {
        this.orderRepository = orderRepository;
        this.voiceNoteService = voiceNoteService;
    }

    /**
     * Retrieve all orders, optionally filtered by status.
     * 
     * @param status (optional) the status filter (e.g., PENDING)
     * @return List of orders
     */
    public List<Order> getAllOrders(String status) {
        if (status == null || status.trim().isEmpty()) {
            return orderRepository.findAll();
        } else {
            // Convert status string to enum value
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            return orderRepository.findByStatus(orderStatus);
        }
    }

    /**
     * Retrieve an order by its ID.
     * 
     * @param orderId the order identifier
     * @return the Order object
     */
    public Order getOrderById(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        return orderOpt.orElseThrow(() -> new RuntimeException("Order not found"));
    }

    /**
     * Update the status of an order.
     * 
     * @param orderId the order identifier
     * @param newStatus the new status (e.g., ACCEPTED, REJECTED)
     * @param adminNote an optional note from the admin (if needed)
     * @return the updated Order object
     */
    public Order updateOrderStatus(Long orderId, String newStatus, String adminNote) {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.valueOf(newStatus.toUpperCase()));
        // Optionally, you can store adminNote in the order if you add that field.
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    /**
     * Retrieves dashboard statistics such as the count of orders by status.
     *
     * @return DashboardStats object containing counts of pending, accepted, and rejected orders.
     */
    public DashboardStats getDashboardStats() {
        int pending = orderRepository.countByStatus(OrderStatus.PENDING);
        int accepted = orderRepository.countByStatus(OrderStatus.ACCEPTED);
        int rejected = orderRepository.countByStatus(OrderStatus.REJECTED);
        return new DashboardStats(pending, accepted, rejected);
    }


    // Method to create an order
    public Order createOrder(OrderRequest orderRequest, MultipartFile voiceNoteFile) {
        try {

            byte[] fileData = voiceNoteFile.getBytes();
            String originalFileName = voiceNoteFile.getOriginalFilename();

            // Upload the voice note and retrieve its URL
            String voiceNoteUrl = voiceNoteService.uploadFile(fileData, originalFileName);

            // Build the Measurements object
            Measurements measurements = new Measurements(
                    orderRequest.getBust(),
                    orderRequest.getWaist(),
                    orderRequest.getHips(),
                    orderRequest.getHeight()
            );

            // Create a new Order object. Default status is PENDING.
            Order order = new Order(
                    orderRequest.getCustomerId(),
                    measurements,
                    voiceNoteUrl,
                    orderRequest.getTimeSlotId(),
                    OrderStatus.PENDING
            );
            order.setCreatedAt(LocalDateTime.now());
            order.setUpdatedAt(LocalDateTime.now());

            // Save the order to the database
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves orders for a specific customer. If status is provided,
     * filters orders by the given status.
     *
     * @param customerId the customer's ID.
     * @param status (optional) the status of orders to filter by.
     * @return List of orders matching the criteria.
     */
    public List<Order> getOrdersForCustomer(Long customerId, String status) {
        if (status == null || status.trim().isEmpty()) {
            return orderRepository.findByCustomerId(customerId);
        } else {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            return orderRepository.findByCustomerIdAndStatus(customerId, orderStatus);
        }
    }
}
