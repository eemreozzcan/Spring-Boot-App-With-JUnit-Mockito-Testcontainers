package com.eemrezcn.example.repository;

import com.eemrezcn.example.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/*This code snippet defines an interface named EmployeeRepository derived from the ReactiveCrudRepository interface,
which is used to interact with a specific collection representing the Employee class.*/
public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String> {
}
