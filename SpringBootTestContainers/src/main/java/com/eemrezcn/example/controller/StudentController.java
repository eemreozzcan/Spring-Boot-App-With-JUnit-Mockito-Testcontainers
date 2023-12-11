package com.eemrezcn.example.controller;

import com.eemrezcn.example.entity.Student;
import com.eemrezcn.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController
{
    @Autowired
    private StudentRepository studentRepository;


    @PostMapping
    public Student createStudent(Student student)
    {
        return studentRepository.save(student);
    }
}
