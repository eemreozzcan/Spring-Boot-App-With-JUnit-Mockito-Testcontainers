package com.eemrezcn.example.repository;

import com.eemrezcn.example.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/*This Interface Automatically Provides A Set Of Standard Crud Operations (Create, Read, Update, Delete) For Database Operations Related
To The Student Class. Spring Data Jpa Simplifies The Implementation Of Basic Database Operations On A Specific Entity Class Using Such Interfaces.
 A Class Implementing This Interface Can Add Or Customize Behavior For Basic Crud Operations.*/
public interface StudentRepository  extends JpaRepository<Student, Long> {
}
