package com.bts.tailor.repo;

import com.bts.tailor.model.Order;
import com.bts.tailor.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
    int countByStatus(OrderStatus status);
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByCustomerIdAndStatus(Long customerId, OrderStatus status);
}