package com.tbs.service;

import com.tbs.repository.CustomerMeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerMeasurementService {

    @Autowired
    private CustomerMeasurementsRepository customerMeasurementsRepository;

    public void saveCustomerMeasurement() {

    }
}
