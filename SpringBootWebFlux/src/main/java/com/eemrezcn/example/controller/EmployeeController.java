package com.eemrezcn.example.controller;

import com.eemrezcn.example.dto.EmployeeDto;
import com.eemrezcn.example.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController /*It Is Used To Define A Controller Class In The Spring Framework*/
@RequestMapping("/api/employees") //This code indicates that in a Java web application developed using the Spring Framework, a method is defined to handle HTTP requests at the "/api/employees" endpoint.
@AllArgsConstructor //@AllArgsConstructor is a Java annotation provided by the Lombok library, and it generates a constructor for all fields in the class.
public class EmployeeController {

    private EmployeeService employeeService;

   /*This code snippet represents a controller method in a Java web application developed with the Spring Framework.
   The method handles HTTP POST requests, saves an employee using the provided EmployeeDto data,
   and sets the HTTP response status to 201 Created upon successful registration.*/
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
        return employeeService.saveEmployee(employeeDto);
    }

    /*This code snippet represents a controller method in a Java web application developed with the Spring Framework.
    The method, annotated with @GetMapping, handles HTTP GET requests corresponding to the specified "id" path variable and calls
    the employeeService.getEmployee(employeeId) method to retrieve information about the relevant employee.*/
    @GetMapping("{id}")
    public Mono<EmployeeDto> getEmployee(@PathVariable("id") String employeeId){
        return employeeService.getEmployee(employeeId);
    }

    /*This code defines a controller method in a Java web application using the Spring Framework. The method, annotated with @GetMapping,
    handles HTTP GET requests without a specific path, likely corresponding to the base path for employee resources.
    The method calls employeeService.getAllEmployees() to retrieve and return a Flux of EmployeeDto objects, indicating that it handles
    multiple employees asynchronously.*/
    @GetMapping
    public Flux<EmployeeDto> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    /*This code represents a controller method in a Java web application using the Spring Framework. The method, annotated with @PutMapping,
    handles HTTP PUT requests for updating an employee with a specific "id" path variable. It takes in an EmployeeDto object from the request
    body and the employee ID from the path, and then calls employeeService.updateEmployee(employeeDto, employeeId) to perform the update operation.
    The method returns a Mono<EmployeeDto>, indicating that it handles the operation asynchronously.*/
    @PutMapping("{id}")
    public Mono<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto,
                                            @PathVariable("id") String employeeId){
        return employeeService.updateEmployee(employeeDto, employeeId);
    }

    /*This code defines a controller method in a Java web application using the Spring Framework. The method, annotated with @DeleteMapping,
     handles HTTP DELETE requests for deleting an employee with a specific "id" path variable. It calls employeeService.deleteEmployee(employeeId)
     to perform the deletion operation, and it sets the HTTP response status to 204 No Content upon successful deletion.
     The method returns a Mono<Void>, indicating that it handles the operation asynchronously and doesn't produce any response body.*/
    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEmployee(@PathVariable("id") String employeeId){
        return employeeService.deleteEmployee(employeeId);
    }
}
