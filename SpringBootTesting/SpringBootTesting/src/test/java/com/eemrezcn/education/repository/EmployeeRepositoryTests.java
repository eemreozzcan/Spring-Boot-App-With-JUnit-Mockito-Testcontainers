package com.eemrezcn.education.repository;


import com.eemrezcn.education.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest //"This Java Spring Boot Annotation Is Used To Test Database Operations Based On Jpa (Java Persistence Api)."
public class EmployeeRepositoryTests {

    //This code injects a Spring-managed EmployeeRepository component.
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    /*The @BeforeEach annotated setUp method is used as a preparation method that runs before each test method, creating a specific Employee
    object with a predetermined first name, last name, and email each time.*/
    @BeforeEach
    public void setUp(){
        employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail,com")
                .build();
    }

    /*This JUnit test verifies the "save employee" operation. It creates an employee object, saves it to the repository using the save method,
    and then checks whether the saved employee object is not null and has been assigned a valid ID (greater than 0).*/
    //@DisplayName("JUnit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

         /*Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Ramesh")
                .email("ramesh@gmail,com")
                .build();*/
        Employee savedEmployee = employeeRepository.save(employee);

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    /*This JUnit test validates the "get all employees" operation. It prepares a list of employees, saves them to the repository, and then retrieves
    the entire list using the findAll method. The test checks that the obtained list is not null and has a size equal to the expected count,
    ensuring that the "get all employees" operation is functioning correctly.*/
    @DisplayName("JUnit test for get all employees operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList(){
        // given - precondition or setup
        // Employee employee = Employee.builder()
        // .firstName("Ramesh")
        // .lastName("Ramesh")
        // .email("ramesh@gmail,com")
        // .build();

        Employee employee1 = Employee.builder()
                .firstName("Ali")
                .lastName("Ozcan")
                .email("ali@gmail,com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        List<Employee> employeeList = employeeRepository.findAll();

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }

    /*This JUnit test verifies the "get employee by id" operation. It creates an employee object, saves it to the repository, and then retrieves
    the employee by its ID using the findById method. The test checks that the obtained employee object is not null, confirming the successful execution
    of the "get employee by id" operation.*/
    @DisplayName("JUnit test for get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){
        /*Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Ramesh")
                .email("ramesh@gmail,com")
                .build();*/
        employeeRepository.save(employee);
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        assertThat(employeeDB).isNotNull();
    }

    /*This JUnit test validates the "get employee by email" operation. It creates an employee object, saves it to the repository, and then retrieves
    the employee by its email address using the findByEmail method. The test checks that the obtained employee object is not null,
    confirming the successful execution of the "get employee by email" operation.*/
    @DisplayName("JUnit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
        // given - precondition or setup
        /*Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail,com")
                .build();*/
        employeeRepository.save(employee);

        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        assertThat(employeeDB).isNotNull();
    }

    /*This JUnit test assesses the "update employee" operation. It creates an employee object, saves it to the repository, retrieves the saved
    employee by its ID, updates its email and first name, and then saves the changes using the save method. The test checks that the updated
    employee has the expected values for email and first name, ensuring the successful execution of the "update employee" operation.*/
    @DisplayName("JUnit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
       /* Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail,com")
                .build();*/
        employeeRepository.save(employee);

        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("ram@gmail.com");
        savedEmployee.setFirstName("Ram");
        Employee updatedEmployee =  employeeRepository.save(savedEmployee);

        assertThat(updatedEmployee.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ram");
    }

    /*This JUnit test validates the "delete employee" operation. It creates an employee object, saves it to the repository, deletes the employee by
    its ID using the deleteById method, and then checks that attempting to find the employee by ID results in an empty optional, confirming
    the successful execution of the "delete employee" operation.*/
    @DisplayName("JUnit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee(){
        // given - precondition or setup
        /*Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail,com")
                .build();*/
        employeeRepository.save(employee);
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        assertThat(employeeOptional).isEmpty();
    }

    /*This JUnit test examines the execution of a custom query using JPQL (Java Persistence Query Language) with an index. It creates an employee object,
    saves it to the repository, and then retrieves the employee using a specific first name and last name with a custom JPQL query.
    The test checks that the obtained employee object is not null, confirming the successful execution of the custom JPQL query with an index.*/
    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject(){
        // given - precondition or setup
        /*Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail,com")
                .build();*/
        employeeRepository.save(employee);
        String firstName = "Ramesh";
        String lastName = "Fadatare";

        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);
        assertThat(savedEmployee).isNotNull();
    }

    /*This JUnit test validates the execution of a custom query using JPQL (Java Persistence Query Language) with named parameters.
    It creates an employee object, saves it to the repository, and then retrieves the employee using a specific first name and last name with
     named parameters in a custom JPQL query. The test checks that the obtained employee object is not null, confirming the successful execution
     of the custom JPQL query with named parameters.*/
    @DisplayName("JUnit test for custom query using JPQL with Named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){
        // given - precondition or setup
       /* Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail,com")
                .build();*/
        employeeRepository.save(employee);
        String firstName = "Ramesh";
        String lastName = "Fadatare";

        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);
        assertThat(savedEmployee).isNotNull();
    }

    /*This JUnit test evaluates the execution of a custom query using native SQL with an index. It creates an employee object,
    saves it to the repository, and then retrieves the employee using a native SQL query with specific parameters (first name and last name).
    The test checks that the obtained employee object is not null, confirming the successful execution of the custom native SQL query with an index.*/
    @DisplayName("JUnit test for custom query using native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject(){
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail,com")
                .build();
        employeeRepository.save(employee);
        // String firstName = "Ramesh";
        // String lastName = "Fadatare";

        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());
        assertThat(savedEmployee).isNotNull();
    }

    /*This JUnit test verifies the execution of a custom query using native SQL with named parameters. It creates an employee object, saves it to the
     repository, and then retrieves the employee using a native SQL query with named parameters (specifically, first name and last name).
     The test checks that the obtained employee object is not null, confirming the successful execution of the custom native SQL query with named parameters.*/
    @DisplayName("JUnit test for custom query using native SQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject(){

        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail,com")
                .build();
        employeeRepository.save(employee);
        // String firstName = "Ramesh";
        // String lastName = "Fadatare";


        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(), employee.getLastName());

        assertThat(savedEmployee).isNotNull();
    }

}