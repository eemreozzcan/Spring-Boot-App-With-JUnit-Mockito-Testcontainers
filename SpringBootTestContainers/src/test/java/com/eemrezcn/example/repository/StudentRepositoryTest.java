package com.eemrezcn.example.repository;

import com.eemrezcn.example.AbstractContainerBaseTest;
import com.eemrezcn.example.entity.Student;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //"This Java Spring Boot Annotation Is Used To Test Database Operations Based On Jpa (Java Persistence Api)."

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //"This Java Spring Boot Annotation Is Used To Automatically Configure The Test Database Setup."
class StudentRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private StudentRepository studentRepository;

    /*This Code Represents A Test Method For Saving A Student Object To The Database. The Test Creates A Student Object (With The Name 'emre',
    Last Name 'ozcan', And Email 'eozcan@hotmail.com'), Saves This Student Object To The Database, And Then Confirms That The Saved
    Student Object Is Not Null And Has An Assigned Identifier. During The Testing Process, Expected Outputs Are Verified Using Assertions.*/
    @Test
    public void givenStudentObject_whenSave_thenReturnSavedStudent() {
        Student student = Student.builder().firstName("Emre")
                .lastName("Ozcan").email("eozcan@hotmail.com").build();

        //when-action or the testing
        Student savedStudent = studentRepository.save(student);

        //then-very output
        Assertions.assertNotNull(savedStudent);
        Assertions.assertNotNull(savedStudent.getId());
    }

    /*This Code Represents A Test Method For Saving A Student Object To The Database And Then Retrieving This Student Based On The Identifier.
    The Test Creates A Student Object, Saves This Student To The Database, Retrieves The Saved Student From The Database Using The Identifier,
    And Finally Verifies That The Retrieved Student Object Is Not Null. During The Testing Process, Expected Outputs Are Checked Using Assertions.*/
    @Test
    public void givenStudentId_whenfindById_thenReturnSavedStudent() {
        Student student = Student.builder().firstName("Emre")
                .lastName("Ozcan").email("eozcan@hotmail.com").build();
        Student savedStudent = studentRepository.save(student);

        //when-action or the testing
        Student studentDB = studentRepository.findById(savedStudent.getId()).get();

        //then-very output
        Assertions.assertNotNull(studentDB);

    }
}