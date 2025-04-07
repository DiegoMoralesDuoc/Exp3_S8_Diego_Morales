package com.fullstack.clases.Antiguos.Student.service;

import java.util.List;
import java.util.Optional;

import com.fullstack.clases.Antiguos.Student.model.Student;

public interface StudentService {
    List <Student> getAllStudents();
    Optional <Student> getStudentById (Long id);
    Student createStudent (Student student);
    Student updateStudent (Long id, Student student);
    void deleteStudent (Long id);
}
