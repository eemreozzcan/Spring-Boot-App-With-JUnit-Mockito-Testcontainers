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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private StudentRepository studentRepository;

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