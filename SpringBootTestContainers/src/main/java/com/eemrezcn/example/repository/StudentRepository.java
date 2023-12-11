package com.eemrezcn.example.repository;

import com.eemrezcn.example.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository  extends JpaRepository<Student, Long> {
}
