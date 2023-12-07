package com.eemrezcn.education.services;

import com.eemrezcn.education.dto.CustomerDTO;

import java.util.UUID;

public interface CustomerService {
    CustomerDTO getCustomerById(UUID customerId);

    CustomerDTO saveNewCustomer(CustomerDTO customerDTO);

    void updateCustomer(UUID customerId, CustomerDTO customerDto);

    void deleteById(UUID customerId);
}