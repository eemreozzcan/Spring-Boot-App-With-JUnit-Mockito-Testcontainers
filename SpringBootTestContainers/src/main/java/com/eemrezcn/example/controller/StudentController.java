package com.eemrezcn.example.controller;

import com.eemrezcn.example.entity.Student;
import com.eemrezcn.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*A Simple Spring Controller Class That Performs The Operations Of Creating And Retrieving Student Records İn A RESTful API*/
@RestController
@RequestMapping("/api/students")
public class StudentController
{

    /*The purpose of this code is to inject an instance of the StudentRepository class into a private field named studentRepository.
     This injection allows other methods within this class to perform database operations using this repository.*/
    @Autowired
    private StudentRepository studentRepository;


    /*The Purpose Of The Method Is To Save The Incoming Student Information To The Database Upon Receiving An Http Post
    Request And Return The Created Student Object As An Http Response*/
    //http://localhost:8080/api/students
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(Student student)
    {
        return studentRepository.save(student);
    }

    /*The General Purpose Of This Method Is To Fetch All Student Records From The Database Upon Receiving An Http
    Get Request And Return These Records As A List In The Http Response.*/
    //http://localhost:8080/api/students
    @GetMapping
    public List<Student> getAllStudents()
    {
        return studentRepository.findAll();
    }

}
