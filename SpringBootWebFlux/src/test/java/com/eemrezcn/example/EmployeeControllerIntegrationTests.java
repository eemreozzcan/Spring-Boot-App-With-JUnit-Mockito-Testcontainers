package com.eemrezcn.example;

import com.eemrezcn.example.dto.EmployeeDto;
import com.eemrezcn.example.repository.EmployeeRepository;
import com.eemrezcn.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

/*This annotation is used to ensure that a Spring Boot application runs on a random port during a test. The @SpringBootTest annotation, along with
the webEnvironment parameter set to SpringBootTest.WebEnvironment.RANDOM_PORT, indicates that the application runs on a randomly chosen available port.
This is commonly used in integration tests to allow the application to run on a different port for each test, preventing interference between different tests.*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class EmployeeControllerIntegrationTests {

    //This code snippet automatically injects the service component of type EmployeeService to be used in a Spring application.
    @Autowired
    private EmployeeService employeeService;

    //This code snippet automatically injects the component of type WebTestClient to be used for Spring WebFlux tests.
    @Autowired
    private WebTestClient webTestClient;

    //This code snippet automatically injects the repository component of type EmployeeRepository to be used in a Spring application.
    @Autowired
    private EmployeeRepository employeeRepository;

    /*This code snippet creates a setup method to run before each test, printing "Before Each Test" and deleting all data from the employeeRepository.*/
    @BeforeEach
    public void before(){
        System.out.println("Before Each Test");
        employeeRepository.deleteAll().subscribe();
    }

    /*This test method simulates saving an employee by sending an HTTP POST request to the "/api/employees" URI using WebTestClient.
    It then verifies the information of the created employee and expects a successful response (HTTP 201 Created).*/
    @Test
    public void testSaveEmployee(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Emre");
        employeeDto.setLastName("Ozcan");
        employeeDto.setEmail("eozcan@gmail.com");

        webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    /*This test method, after saving an employee, sends an HTTP GET request to the "/api/employees/{id}" URI using WebTestClient.
    It then verifies receiving a successful response (HTTP 200 OK) along with the expected information of the employee.*/
    @Test
    public void testGetSingleEmployee(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Ali");
        employeeDto.setLastName("Ozcan");
        employeeDto.setEmail("aozcan@gmail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        webTestClient.get().uri("/api/employees/{id}", Collections.singletonMap("id",savedEmployee.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    /*This test method, after saving a couple of employees, sends an HTTP GET request to the "/api/employees" URI using WebTestClient.
    It then verifies receiving an expected HTTP 200 OK response and prints the list of EmployeeDto objects in the console.*/
    @Test
    public void testGetAllEmployees(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Osman");
        employeeDto.setLastName("Ozcan");
        employeeDto.setEmail("oozcan@gmail.com");

        employeeService.saveEmployee(employeeDto).block();

        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setFirstName("Halil");
        employeeDto1.setLastName("Ozcan");
        employeeDto1.setEmail("hozcan@gmail.com");

        employeeService.saveEmployee(employeeDto1).block();

        webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println);
    }

    /*This test method, after saving an employee, sends an HTTP PUT request to the "/api/employees/{id}" URI using WebTestClient.
     It then verifies receiving a successful response (HTTP 200 OK) with the expected information and updates the employee's details in the process.*/
    @Test
    public void testUpdateEmployee(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Sezer");
        employeeDto.setLastName("Ozcan");
        employeeDto.setEmail("sozcan@gmail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        EmployeeDto updatedEmployee = new EmployeeDto();
        updatedEmployee.setFirstName("Arda");
        updatedEmployee.setLastName("Ozcan");
        updatedEmployee.setEmail("aardaozcan@gmail.com");

        webTestClient.put().uri("/api/employees/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(updatedEmployee), EmployeeDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(updatedEmployee.getFirstName())
                .jsonPath("$.lastName").isEqualTo(updatedEmployee.getLastName())
                .jsonPath("$.email").isEqualTo(updatedEmployee.getEmail());
    }

    /*This test method, after saving an employee, sends an HTTP DELETE request to the "/api/employees/{id}" URI using WebTestClient.
    It then verifies receiving the expected No Content response and deletes the employee in the process.*/
    @Test
    public void testDeleteEmployee(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Burak");
        employeeDto.setLastName("Ozcan");
        employeeDto.setEmail("bozcan1@gmail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        webTestClient.delete().uri("/api/employees/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);

    }
}
