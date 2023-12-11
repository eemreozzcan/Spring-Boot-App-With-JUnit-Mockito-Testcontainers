package com.eemrezcn.example.controller;

import com.eemrezcn.example.entity.Student;
import com.eemrezcn.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController
{
    @Autowired
    private StudentRepository studentRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(Student student)
    {
        return studentRepository.save(student);
    }

    @GetMapping
    public List<Student> getAllStudents()
    {
        return studentRepository.findAll();
    }

}
