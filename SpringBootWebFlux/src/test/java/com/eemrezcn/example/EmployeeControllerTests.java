package com.eemrezcn.example;

import com.eemrezcn.example.controller.EmployeeController;
import com.eemrezcn.example.dto.EmployeeDto;
import com.eemrezcn.example.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*This annotation is used in JUnit 5 tests to support integration with Spring, enabling the test to initialize the Spring context.*/
@ExtendWith(SpringExtension.class)
/*The statement "@WebFluxTest(controllers = EmployeeController.class) is used in Spring Boot to conduct unit tests related to WebFlux.
This annotation automatically applies the necessary configurations for testing the EmployeeController class.*/
@WebFluxTest(controllers = EmployeeController.class)

public class EmployeeControllerTests {

    //This code snippet automatically injects the component of type WebTestClient to be used for Spring WebFlux tests.
    @Autowired
    private WebTestClient webTestClient;

    //This Code Injects A Mock (Fake) employeeservice Component Managed By The Spring Testcontext Framework.
    @MockBean
    private EmployeeService employeeService;


    /*This test method expects to save an employee object and return the saved employee object.
    It uses BDDMockito to simulate the behavior of the saveEmployee method, and then sends an HTTP POST request to save the employee.
    Finally, it verifies receiving a successful response with the expected information.*/
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Emre");
        employeeDto.setLastName("Ozcan");
        employeeDto.setEmail("emre@gmail.com");

        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(EmployeeDto.class)))
                .willReturn(Mono.just(employeeDto));


        WebTestClient.ResponseSpec response = webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange();

        response.expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    /*This test method expects to retrieve a list of employees when given a list of employee objects.
    It uses BDDMockito to simulate the behavior of the getAllEmployees method, then sends an HTTP GET request to the "/api/employees" URI.
    Finally, it verifies receiving an expected HTTP 200 OK response and prints the list of employee objects to the console.*/
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_returnListOfEmployees(){

        List<EmployeeDto> list = new ArrayList<>();
        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setFirstName("Emre");
        employeeDto1.setLastName("Ozcan");
        employeeDto1.setEmail("eozcan@gmail.com");
        list.add(employeeDto1);

        EmployeeDto employeeDto2 = new EmployeeDto();
        employeeDto2.setFirstName("Ali");
        employeeDto2.setLastName("Ozcan");
        employeeDto2.setEmail("aozcan@gmail.com");
        list.add(employeeDto2);

        Flux<EmployeeDto> employeeFlux = Flux.fromIterable(list);

        BDDMockito.given(employeeService.getAllEmployees())
                .willReturn(employeeFlux);


        WebTestClient.ResponseSpec response = webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();


        response.expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println);
    }

    /*This test method expects to receive the updated employee object when given an updated employee, simulating the behavior of
    the updateEmployee method using BDDMockito. It then sends an HTTP PUT request to the "/api/employees/{id}" URI,
    verifying the expected HTTP 200 OK response and printing the updated employee object to the console.*/
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployeeObject(){

        String employeeId = "123";

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Ayse");
        employeeDto.setLastName("Ozcan");
        employeeDto.setEmail("ayseozcan@gmail.com");

        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(EmployeeDto.class),
                        ArgumentMatchers.any(String.class)))
                .willReturn(Mono.just(employeeDto));

        WebTestClient.ResponseSpec response = webTestClient.put().uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto),EmployeeDto.class)
                .exchange();

       response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    /*This test method, given an employee ID, expects to perform the employee deletion operation with no return value.
    It simulates the behavior of the deleteEmployee method using BDDMockito. Then, it sends an HTTP DELETE request to the "/api/employees/{id}" URI
    and verifies receiving the expected  No Content response, printing that there is no return value to the console.*/
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnNothing(){

        String employeeId = "123";
        Mono<Void> voidMono = Mono.empty();
        BDDMockito.given(employeeService.deleteEmployee(employeeId))
                .willReturn(voidMono);

        WebTestClient.ResponseSpec response = webTestClient
                .delete()
                .uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
                .exchange();

        response.expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }
}
