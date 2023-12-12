package com.eemrezcn.example.service.impl;

import com.eemrezcn.example.dto.EmployeeDto;
import com.eemrezcn.example.entity.Employee;
import com.eemrezcn.example.mapper.EmployeeMapper;
import com.eemrezcn.example.repository.EmployeeRepository;
import com.eemrezcn.example.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service //This code snippet defines a class representing a service in the Spring Framework, typically responsible for executing business logic operations
@AllArgsConstructor //@AllArgsConstructor is a Java annotation provided by the Lombok library, and it generates a constructor for all fields in the class.
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    /*This code snippet comprises the implementation of a method that takes an EmployeeDto object, converts it into an Employee entity,
    saves this entity using employeeRepository.save, and, along with the resulting Mono<Employee>, t
    ransforms the saved employee back into an EmployeeDto.*/
    @Override
    public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto) {
        // convert EmployeeDTO into Employee Entity
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Mono<Employee> savedEmployee = employeeRepository.save(employee);
        return savedEmployee
                .map((employeeEntity) -> EmployeeMapper.mapToEmployeeDto(employeeEntity));
    }

    /*This method retrieves a specific employee by their ID using employeeRepository.findById(employeeId), then transforms the resulting
    Mono<Employee> by applying the EmployeeMapper.mapToEmployeeDto method to convert the retrieved employee into a Mono<EmployeeDto>,
    and finally returns it.*/
    @Override
    public Mono<EmployeeDto> getEmployee(String employeeId) {
        Mono<Employee> savedEmployee = employeeRepository.findById(employeeId);
        return savedEmployee
                .map((employee) -> EmployeeMapper.mapToEmployeeDto(employee));
    }

    /*This method retrieves all employees using employeeRepository.findAll(), transforms each employee by applying the EmployeeMapper.mapToEmployeeDto
    method using the obtained Flux<Employee>, converts it into a Flux<EmployeeDto>, and returns an empty Flux using Flux.empty()*/
    @Override
    public Flux<EmployeeDto> getAllEmployees() {

        Flux<Employee> employeeFlux = employeeRepository.findAll();

        return employeeFlux
                .map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
                .switchIfEmpty(Flux.empty());
    }

    /*This method retrieves an existing employee by their ID using employeeRepository.findById(employeeId). Subsequently,
    it updates the information of the existing employee, saves it using employeeRepository.save, and then returns
    the resulting updated Mono<Employee> transformed into a Mono<EmployeeDto> using the EmployeeMapper.mapToEmployeeDto method.*/
    @Override
    public Mono<EmployeeDto> updateEmployee(EmployeeDto employeeDto, String employeeId) {

        Mono<Employee> employeeMono = employeeRepository.findById(employeeId);

        Mono<Employee> updatedEmployee = employeeMono.flatMap((existingEmployee) -> {
            existingEmployee.setFirstName(employeeDto.getFirstName());
            existingEmployee.setLastName(employeeDto.getLastName());
            existingEmployee.setEmail(employeeDto.getEmail());

            return employeeRepository.save(existingEmployee);
        });
        return updatedEmployee
                .map((employee) -> EmployeeMapper.mapToEmployeeDto(employee));
    }

    /*This method deletes an employee with a specific ID using employeeRepository.deleteById(employeeId) and returns a result of type Mono<Void>.*/
    @Override
    public Mono<Void> deleteEmployee(String employeeId) {
        return employeeRepository.deleteById(employeeId);
    }
}
