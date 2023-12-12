package com.eemrezcn.education.service;

import com.eemrezcn.education.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeService
{
    /*It Is Used To Save An Employee Object To The Database. It Saves The Provided Employee Object As A Record And Returns The Saved Employee Object.*/
    Employee saveEmployee(Employee employee);

    /*It Is Used To Retrieve All Employee Objects From The Database. It Returns All Employees As A List.*/
    List<Employee> getAllEmployees();

    /*It Is Used To Retrieve An Employee Object From The Database With The Specified Id. It Returns The Employee Object With The Given Id Inside An Optional<employee>.*/
    Optional<Employee> getEmployeeById(Long id);

    /*It Is Used To Save An Updated Employee Object To The Database. It Saves The Provided Updated Employee Object As A Record And Returns The Saved Employee Object.*/
    Employee updateEmployee(Employee updatedEmployee);

    /*It Is Used To Delete An Employee Object From The Database With The Specified Id. It Deletes The Employee Object With The Given Id As A Parameter.*/
    void deleteEmployee(long id);
}
