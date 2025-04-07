package com.fullstack.clases.Antiguos.Student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstack.clases.Antiguos.Student.model.Student;

public interface StudentRepository extends JpaRepository <Student, Long>{
    
}
