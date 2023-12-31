package com.eemrezcn.example.mapper;

import com.eemrezcn.example.dto.EmployeeDto;
import com.eemrezcn.example.entity.Employee;

/*This code defines an EmployeeMapper class capable of performing conversion between Employee and EmployeeDto classes.
 The mapToEmployeeDto method converts an Employee object to an EmployeeDto, while the mapToEmployee method converts
 an EmployeeDto object to an Employee.*/
public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee){
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail()
        );
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto){
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail()
        );
    }
}
