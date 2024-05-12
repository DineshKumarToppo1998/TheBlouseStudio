package com.tbs.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tbs.service.CustomerMeasurementService;

@RestController
@RequestMapping("/tbs/c-measurements")
public class CustomerMeasurementsController {

    @Autowired
    private CustomerMeasurementService customerMeasurementService;


    @GetMapping("/getAll")
    public void getAllCustomerMeasurements() {
        customerMeasurementService.saveCustomerMeasurement();
    }

}
