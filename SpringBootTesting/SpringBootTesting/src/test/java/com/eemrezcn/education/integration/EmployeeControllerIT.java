package com.eemrezcn.education.integration;


import com.eemrezcn.education.model.Employee;
import com.eemrezcn.education.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.hamcrest.CoreMatchers.is;
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

public class EmployeeControllerIT extends AbstractContainerBaseTest
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

    /*This test method creates an Employee object, sends an HTTP POST request to the '/api/employees' endpoint, and checks whether the employee
    is saved or not. The test results verify if the firstName, lastName, and email properties in the HTTP response match the expected values.*/
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

    /*This test method saves a pre-created list of Employee objects to the database, then sends a GET request to the '/api/employees' endpoint to
    retrieve all employees. It checks whether the returned employee list is correct. The test results verify if the size of the employee
     list in the HTTP response is as expected.*/
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

    /*This test method creates an Employee object, saves it to the database, and then sends a GET request to the '/api/employees/{id}' endpoint
    to retrieve the employee by ID. It checks whether the returned employee object has the expected properties.
    The test results verify if the HTTP response has a status of OK (200) and if the returned employee's firstName, lastName,
    and email match the expected values.*/
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

    /*This test method simulates sending a GET request to retrieve an employee by providing an invalid employee ID.
    It expects that the returned HTTP status is "Not Found" (404). The setup involves saving an employee with a different ID to the database before the test.
    The test checks if the response status is 404, indicating that the employee with the specified ID is not found.*/
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

    /*This test method represents updating an employee by sending an HTTP PUT request. It expects the updated employee information to be returned.
    The setup involves saving an employee to the database before the test. The test then sends a PUT request to update this employee with new information
    and checks if the response status is OK (200) and if the returned employee information matches the updated details.*/
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

    /*This test method checks the scenario where an update is attempted for an employee with an invalid ID (employeeId).
    The setup involves saving an employee to the database with a different ID than the one used in the update request.
    The test sends a PUT request to update the employee with the specified ID and expects a response status of 404 (Not Found) since
     the employee with the given ID does not exist in the database. The test also prints the response details for further inspection.*/
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

    /*This test method validates the scenario where a DELETE request is made to remove an employee with a specific ID (savedEmployee.getId()).
     The setup involves saving an employee to the database with a unique ID. The test then sends a DELETE request to the "/api/employees/{id}" endpoint,
     where {id} is the ID of the saved employee. The expected result is an HTTP status of 200 (OK), indicating that the deletion was successful.
     The test also prints the response details for further inspection.*/
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
