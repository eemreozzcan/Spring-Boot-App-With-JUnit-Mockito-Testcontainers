package com.eemrezcn.education.service;

import com.eemrezcn.education.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeService
{
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(Long id);

    Employee updateEmployee(Employee updatedEmployee);

    void deleteEmployee(long id);
}
