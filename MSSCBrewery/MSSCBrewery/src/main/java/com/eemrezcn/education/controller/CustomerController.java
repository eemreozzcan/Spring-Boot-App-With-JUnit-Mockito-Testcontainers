package com.eemrezcn.education.controller;

import com.eemrezcn.education.dto.CustomerDTO;
import com.eemrezcn.education.services.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/v1/customer")
@RestController
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("customerId")  UUID customerId){

        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity handlePost(CustomerDTO customerDto){

    	CustomerDTO savedDto = customerService.saveNewCustomer(customerDto);

    	HttpHeaders headers = new HttpHeaders();
       	headers.add("Location", "/api/v1/customer/" + savedDto.getId().toString());

    	return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleUpdate(@PathVariable("customerId") UUID customerId,@RequestBody CustomerDTO customerDto){
    	customerService.updateCustomer(customerId, customerDto);

    }

    @DeleteMapping("/{customerId}")
    public void deleteById(@PathVariable("customerId") UUID customerId) {
    	customerService.deleteById(customerId);
    }

}