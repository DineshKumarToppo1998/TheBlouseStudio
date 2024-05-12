package com.tbs.controller;

import com.tbs.dto.CustomerDto;
import com.tbs.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tbs/customer")
public class CustomerController {


    @Autowired
    private CustomerService customerService;

    @GetMapping("/get/{id}")
    public CustomerDto getCustomer(@PathVariable Long id) {
        return customerService.getCustomer(id);
    }

    @PostMapping("/save")
    public ResponseEntity<CustomerDto> saveCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok().body(customerService.saveCustomer(customerDto));
    }

    @PutMapping("/update")
    public String updateCustomer() {


        return "Customer updated";
    }

    @RequestMapping("/delete")
    public String deleteCustomer() {
        return "Customer deleted";
    }

    @RequestMapping("/getAll")
    public String getAllCustomers() {
        return "All Customers";
    }
}
