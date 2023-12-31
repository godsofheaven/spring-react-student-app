package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents()
    {
        return studentRepository.findAll(Sort.by(Sort.Direction.ASC,"id"));
    }

    public void addStudent(Student student) {
        if(!emailIdExists(student)) {
            studentRepository.save(student);
        }
        else{
            throw new BadRequestException("Student with email id "+ student.getEmail() +" already exists.");
        }
    }

    private boolean emailIdExists(Student student) {
        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("email", ignoreCase());
        Student probe = new Student();
        probe.setEmail(student.getEmail());
        Example<Student> example = Example.of(probe, modelMatcher);
        return studentRepository.exists(example);
    }

    public void deleteStudent(Long id) {
        boolean studentExists = studentRepository.existsById(id);
        if(studentExists) {
            studentRepository.deleteById(id);
        }
        else {
            throw new StudentNotFoundException("Unable to find student to be deleted.");
        }
    }
}
