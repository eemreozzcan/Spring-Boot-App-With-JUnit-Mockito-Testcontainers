package com.eemrezcn.education.service.impl;

import com.eemrezcn.education.exception.ResourceNotFoundException;
import com.eemrezcn.education.model.Employee;
import com.eemrezcn.education.repository.EmployeeRepository;
import com.eemrezcn.education.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*"This Code Represents A Spring Service Class Named 'employeeserviceimpl' That Implements The 'employeeservice' Interface."*/
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /*This Method Encompasses A Procedure For Saving An Employee Object. Initially, It Checks Whether The Email Address Of The Employee Object Already
    Exists In The Database. If The Email Address Is Already Present, It Throws A ResourceNotFoundException Exception With An Error Message Indicating
     That An Employee Already Exists With The Given Email. If The Email Address Is Not Already In Use, The Employee Object Is Saved To The Database*/
    @Override
    public Employee saveEmployee(Employee employee) {

        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee already exist with given email:" + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }
    /*"This Method Retrieves All Employee Objects From The Database And Returns Them As A List."*/
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    /*"This Method Retrieves The Employee Object With The Specified Id From The Database."*/
    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
    /*"This Method Is Used To Save An Updated Employee Object."*/
    @Override
    public Employee updateEmployee(Employee updatedEmployee) {
        return employeeRepository.save(updatedEmployee);
    }
    /*This Method Is Used To Delete The Employee Object With The Specified Id.*/
    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }
}
