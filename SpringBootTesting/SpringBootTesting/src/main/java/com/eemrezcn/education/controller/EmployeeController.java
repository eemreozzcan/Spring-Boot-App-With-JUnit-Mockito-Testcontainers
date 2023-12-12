package com.eemrezcn.education.controller;

import com.eemrezcn.education.model.Employee;
import com.eemrezcn.education.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController /*It Is Used To Define A Controller Class In The Spring Framework*/
@RequestMapping("/api/employees") /*It Is Used To Map A Url Path To A Method In A Spring Controller.*/
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /*This Method, Annotated With @PostMapping And @ResponseStatus(Httpstatus.created), Takes An Employee Object Received In An Http Post Request Using @RequestBody.
    It Performs An Operation Using This Information And Returns The Created Employee Object As An Http Response. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    /*This Method, Annotated With @GetMapping, Performs An Operation To List All Employee Objects When An Http Get Request Is Received And Returns
    A List Containing These Objects As The Result.*/
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    /*This Method, Annotated With @GetMapping("{id}"), Performs An Operation To Retrieve A Specific Employee By Its Identifier When An Http Get Request Is Received.
     If The Operation Is Successful, It Returns A ResponsEntity Containing The Found Employee Object; Otherwise,
     It Returns A ResponsEntity With An Error Status Indicating "Not Found."*/
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long employeeId) {
        return employeeService.getEmployeeById(employeeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*This Method Is Used To Update The Information Of A Specific Employee At The Specified Url.The {id} Placeholder Represents The Id Of A Specific Employee
    In The Url. This Annotation Maps An Http Put Request To The Specified Url. The Corresponding Method Updates The Information Of The Employee With The Specified Id
    And Returns The Updated Employee As A Responseentity Object.If An Employee With The Specified Id Cannot Be Found, An Http Status Code Indicating "Not Found"
    Is Returned*/
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long employeeId,
                                                   @RequestBody Employee employee) {
        return employeeService.getEmployeeById(employeeId)
                .map(savedEmployee -> {

                    savedEmployee.setFirstName(employee.getFirstName());
                    savedEmployee.setLastName(employee.getLastName());
                    savedEmployee.setEmail(employee.getEmail());

                    Employee updatedEmployee = employeeService.updateEmployee(savedEmployee);
                    return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*This Method Accepts An Http Delete Request To Delete A Specific Employee And Calls The Corresponding Service Method.
    It Then Returns A Responseentity Object Based On Whether The Deletion Was Successful. */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long employeeId) {

        employeeService.deleteEmployee(employeeId);

        return new ResponseEntity<String>("Employee deleted successfully!.", HttpStatus.OK);

    }

}
