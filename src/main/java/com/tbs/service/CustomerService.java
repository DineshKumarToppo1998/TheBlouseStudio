package com.tbs.service;

import com.tbs.dto.CustomerDto;
import com.tbs.model.Customer;
import com.tbs.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Save A new Customer
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        Customer customer = convertToEntity(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDto(savedCustomer);
    }

    private CustomerDto convertToDto(Customer savedCustomer) {
        return new CustomerDto(
                savedCustomer.getId(),
                savedCustomer.getName(),
                savedCustomer.getEmail(),
                savedCustomer.getPhone(),
                savedCustomer.getAddress()
        );
    }


    private Customer convertToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhone(customerDto.getPhone());
        customer.setAddress(customerDto.getAddress());
        return customer;
    }

    public CustomerDto getCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if(customer != null) {
            return convertToDto(customer);
        } else {
            return null;
        }
    }

    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if(customer != null) {
            customer.setName(customerDto.getName());
            customer.setEmail(customerDto.getEmail());
            customer.setPhone(customerDto.getPhone());
            customer.setAddress(customerDto.getAddress());
            Customer updatedCustomer = customerRepository.save(customer);
            return convertToDto(updatedCustomer);
        } else {
            return null;
        }
    }
}
