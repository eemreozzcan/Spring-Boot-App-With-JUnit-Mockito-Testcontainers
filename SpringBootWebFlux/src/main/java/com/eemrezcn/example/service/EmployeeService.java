package com.eemrezcn.example.service;

import com.eemrezcn.example.dto.EmployeeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {

    //This method represents an operation that takes an EmployeeDto object, saves the employee, and returns the result as a Mono<EmployeeDto>.
    Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto);

    //This method represents an operation that retrieves an employee based on a specific ID and returns the result as a Mono<EmployeeDto>.
    Mono<EmployeeDto> getEmployee(String employeeId);

    //This method represents an operation that retrieves all employees and returns the result as a Flux<EmployeeDto>.
    Flux<EmployeeDto> getAllEmployees();

    //This method represents an operation that updates an employee based on a specific ID using the information from an EmployeeDto and returns the result as a Mono<EmployeeDto>.
    Mono<EmployeeDto> updateEmployee(EmployeeDto employeeDto, String employeeId);

    //This method represents an operation that deletes an employee based on a specific ID and returns the result as a Mono<Void>.
    Mono<Void> deleteEmployee(String employeeId);

}
