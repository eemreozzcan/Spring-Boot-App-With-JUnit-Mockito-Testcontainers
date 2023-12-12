package com.eemrezcn.education.repository;


import com.eemrezcn.education.integration.AbstractContainerBaseTest;
import com.eemrezcn.education.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest //"This Java Spring Boot Annotation Is Used To Test Database Operations Based On Jpa (Java Persistence Api)."
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //"This Java Spring Boot Annotation Is Used To Automatically Configure The Test Database Setup."

public class EmployeeRepositoryIT extends AbstractContainerBaseTest {

    //This code injects a Spring-managed EmployeeRepository component.
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstName("Emre")
                .lastName("Ozcan")
                .email("eozcan@gmail,com")
                .build();
    }


    /*This test method verifies the scenario of saving an Employee object with specific properties to the database.First, the necessary preconditions
    or setups (given) for the test are defined. Then, the specified behavior or action (when) is performed,in this case, the Employee object is saved to
    the database. Finally, the verification of the output (then) is done, checking that the saved Employee object is not null and has an assigned identifier."*/
    //@DisplayName("JUnit test for save employee operation")
    //@Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Emre")
                .lastName("Emre")
                .email("eozcan@gmail,com")
                .build();
        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }


    /*This JUnit test verifies the "get all employees" operation by checking whether the employees stored in the database are successfully retrieved.
     It confirms, with the help of two employee examples used in the test, that the obtained list of employees is not null and contains two elements.*/
    @DisplayName("JUnit test for get all employees operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList(){
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Emre")
//                .lastName("Emre")
//                .email("eozcan@gmail,com")
//                .build();

        Employee employee1 = Employee.builder()
                .firstName("Ali")
                .lastName("Ozcan")
                .email("ali@gmail,com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        // when -  action or the behaviour that we are going test
        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }

    /*This JUnit test verifies the "get employee by id" operation by testing it based on the ID of an employee.It checks whether the employee example
    used in the test is successfully saved to the database and confirms that the obtained employee object is not null.*/
    @DisplayName("JUnit test for get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Emre")
//                .lastName("Emre")
//                .email("eozcan@gmail,com")
//                .build();
        employeeRepository.save(employee);

        // when -  action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    /*This JUnit test examines the "get employee by email" operation by testing it based on an employee's email address. It checks whether the employee
    example used in the test is successfully saved to the database and confirms that the obtained employee object is not null.*/
    @DisplayName("JUnit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Emre")
//                .lastName("Ozcan")
//                .email("eozcan@gmail,com")
//                .build();
        employeeRepository.save(employee);

        // when -  action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    /*This JUnit test verifies the "update employee" operation by testing the updating of an employee's information. After saving the employee example
    used in the test to the database, it updates the information of this employee and confirms whether the updated employee object has the expected values.*/
    @DisplayName("JUnit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        // Employee employee = Employee.builder()
        //        .firstName("Emre")
        //        .lastName("Ozcan")
        //        .email("eozcan@gmail,com")
        //        .build();
        employeeRepository.save(employee);

        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("ram@gmail.com");
        savedEmployee.setFirstName("Ram");
        Employee updatedEmployee =  employeeRepository.save(savedEmployee);

        assertThat(updatedEmployee.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ram");
    }

    /*This JUnit test validates the "delete employee" operation by testing the deletion of an employee. After saving the employee example used in the test
    to the database, it deletes this employee and confirms that the employee is no longer present in the database.*/
    @DisplayName("JUnit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee(){
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Emre")
//                .lastName("Ozcan")
//                .email("eozcan@gmail,com")
//                .build();
        employeeRepository.save(employee);

        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        assertThat(employeeOptional).isEmpty();
    }

    /*This JUnit test examines the execution of a custom query using JPQL (Java Persistence Query Language) with an index. After saving the employee
    example used in the test to the database, it retrieves the employee using a specific first name and last name with a custom JPQL query, confirming
    that the obtained employee object is not null.*/
    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject(){
        // Employee employee = Employee.builder()
        //        .firstName("Emre")
        //        .lastName("Ozcan")
        //        .email("eozcan@gmail,com")
        //        .build();
        employeeRepository.save(employee);
        String firstName = "Ramesh";
        String lastName = "Fadatare";

        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);
        assertThat(savedEmployee).isNotNull();
    }

    /*This JUnit test validates the execution of a custom query using JPQL (Java Persistence Query Language) with named parameters.
    After saving the employee example used in the test to the database, it retrieves the employee using a specific first name and last name
     with named parameters in a custom JPQL query, confirming that the obtained employee object is not null.*/
    @DisplayName("JUnit test for custom query using JPQL with Named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){
        //Employee employee = Employee.builder()
        //           .firstName("Ramesh")
        //           .lastName("Fadatare")
        //           .email("ramesh@gmail,com")
        //           .build();
        employeeRepository.save(employee);
        String firstName = "Ramesh";
        String lastName = "Fadatare";

        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);
        assertThat(savedEmployee).isNotNull();
    }

    /*This JUnit test assesses the execution of a custom query using native SQL with an index. After saving the employee example used in the test
    to the database, it retrieves the employee using a native SQL query with specific parameters (first name and last name),
    confirming that the obtained employee object is not null.*/
    @DisplayName("JUnit test for custom query using native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject(){
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Fadatare")
//                .email("ramesh@gmail,com")
//                .build();
        employeeRepository.save(employee);
        // String firstName = "Ramesh";
        // String lastName = "Fadatare";

        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());
        assertThat(savedEmployee).isNotNull();
    }

    /*This JUnit test evaluates the execution of a custom query using native SQL with named parameters. After saving the employee example used in
    the test to the database, it retrieves the employee using a native SQL query with named parameters (specifically, first name and last name),
     confirming that the obtained employee object is not null.*/
    @DisplayName("JUnit test for custom query using native SQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject(){
        //Employee employee = Employee.builder()
        //            .firstName("Ramesh")
        //            .lastName("Fadatare")
        //            .email("ramesh@gmail,com")
        //            .build();
        employeeRepository.save(employee);
        // String firstName = "Ramesh";
        // String lastName = "Fadatare";

        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(), employee.getLastName());
        assertThat(savedEmployee).isNotNull();
    }

}
