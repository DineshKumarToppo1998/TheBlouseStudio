package com.tbs.repository;

import com.tbs.model.Customer;
import com.tbs.model.CustomerMeasurements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMeasurementsRepository extends JpaRepository<CustomerMeasurements, Long> {
    List<CustomerMeasurements> findByCustomer(Customer customer);
}
