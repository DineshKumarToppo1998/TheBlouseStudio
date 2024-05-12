package com.tbs.repository;

import com.tbs.model.Customer;
import com.tbs.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    // Find all orders for a specific customer
    List<CustomerOrder> findByCustomer(Customer customer);

    //Find a specific order for a customer by ID
    CustomerOrder findByCustomerAndId(Customer customer, Long orderId);
}

