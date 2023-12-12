package com.eemrezcn.education.integration;


import com.eemrezcn.education.model.Employee;
import com.eemrezcn.education.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/*This annotation is used to ensure that a Spring Boot application runs on a random port during a test. The @SpringBootTest annotation, along with
the webEnvironment parameter set to SpringBootTest.WebEnvironment.RANDOM_PORT, indicates that the application runs on a randomly chosen available port.
This is commonly used in integration tests to allow the application to run on a different port for each test, preventing interference between different tests.*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //"The @AutoConfigureMockMvc annotation is used to automatically configure and provide a MockMvc instance in the Spring test context.
                      // MockMvc allows you to perform HTTP requests against your application in Spring MVC tests."
public class EmployeeControllerITests
{
    //This Code Injects An Instance Of The Mockmvc Class, Which Is Managed By The Spring Framework And Enables The Testing Of Controllers In Spring Mvc Applications.
    @Autowired
    private MockMvc mockMvc;

    //This code injects a Spring-managed EmployeeRepository component.
    @Autowired
    private EmployeeRepository employeeRepository;

    //"This Code Injects An Instance Of The Objectmapper Class Managed By The Spring Framework.
    // Objectmapper Is Used To Convert Java Objects To Json Format And Vice Versa."
    @Autowired
    private ObjectMapper objectMapper;

    //"The purpose of this method is to delete all Employee records in the database before each test starts, ensuring a clean starting point for the tests."
    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
    }

    /*This test method verifies whether a new employee is successfully created by sending an HTTP POST request with data representing an employee object
    with specific attributes. The test checks the response of the request to the designated API endpoint and ensures that the response contains
     the expected employee properties. Thus, it represents a test scenario confirming whether an employee is successfully created.*/
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();


        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        response.andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                        CoreMatchers.is(employee.getEmail())));


    }

    /*This test method ensures that when a list of employees is prepopulated in the database, sending a GET request to the '/api/employees' endpoint
    returns the correct list of employees. The test sets up the initial condition by saving a list of employees in the database, then sends
    a GET request to retrieve all employees from the specified API endpoint. Finally, it checks whether the response status is OK (200) and
    verifies that the size of the returned employee list matches the expected size.*/
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
        // given - precondition or setup
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("Ramesh").lastName("Fadatare").email("ramesh@gmail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Tony").lastName("Stark").email("tony@gmail.com").build());
        employeeRepository.saveAll(listOfEmployees);

        ResultActions response = mockMvc.perform(get("/api/employees"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));

    }
    /*This test method verifies that when a specific employee ID is provided, sending a GET request to the '/api/employees/{id}' endpoint returns
    the corresponding employee object. The test sets up the initial condition by saving an employee with the specified ID in the database,
     then sends a GET request to retrieve the employee by ID from the specified API endpoint.Finally, it checks whether the response status is OK (200)
     and verifies that the returned employee object's firstName, lastName, and email match the expected values.*/
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {

        Employee employee = Employee.builder()
                .id(1L)  // Set the id explicitly
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();
        employeeRepository.save(employee);

         ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    /*This test method validates that when an invalid employee ID is provided, sending a GET request to the '/api/employees/{id}' endpoint returns an
    empty result (404 Not Found). The test sets up the initial condition by saving an employee with a different ID in the database, then sends
    a GET request to retrieve an employee with the specified invalid ID from the specified API endpoint. Finally, it checks whether the
    response status is 404 (Not Found), indicating that no employee with the given ID was found.*/
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {
        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();
        employeeRepository.save(employee);

        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    /*This test method verifies that when an employee is updated by sending an HTTP PUT request to the '/api/employees/{id}' endpoint,
    the updated employee's information is correctly returned. The test involves creating and saving an initial employee (savedEmployee) to the database,
    updating this employee's information, and then sending an HTTP PUT request to update the employee with the specified ID.
    Finally, the method checks whether the response status is 200 (OK) and if the returned JSON matches the expected updated employee information.*/
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception {


        Employee savedEmployee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .firstName("Ram")
                .lastName("Jadhav")
                .email("ram@gmail.com")
                .build();


        ResultActions response = mockMvc.perform(put("/api/employees/{id}", savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));


        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
    }

    /*This test method checks the scenario where an attempt is made to update an employee with a specified ID that does not exist in the database.
    It involves creating and saving an initial employee (savedEmployee) to the database, attempting to update this employee with a different ID (employeeId),
    and then sending an HTTP PUT request. The method verifies whether the response status is 404 (Not Found),
    indicating that the employee with the specified ID was not found for updating.*/
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .firstName("Ram")
                .lastName("Jadhav")
                .email("ram@gmail.com")
                .build();

        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    /*This test method checks the scenario where an attempt is made to delete an employee with a specified ID from the database.
    It involves creating and saving an initial employee (savedEmployee) to the database, sending an HTTP DELETE request for the specified employee ID,
    and then verifying whether the response status is 200 (OK), indicating that the deletion was successful.*/
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception{

        Employee savedEmployee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", savedEmployee.getId()));

        response.andExpect(status().isOk())
                .andDo(print());
    }
}
