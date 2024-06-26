package com.cache.redis_cache_demo;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Cacheable(value = "students", key = "#id")
    public Student getStudentById(Long id) {
        // Simulating database access
        System.out.println("Fetching student with id=" + id + " from database.");
        return studentRepository.findById(id).orElse(null);
    }

    @CachePut(value = "students", key = "#student.id")
    public Student saveStudent(Student student) {
        // Simulating database save
        System.out.println("Saving student with id=" + student.getId() + " to database.");
        return studentRepository.save(student);
    }

    @CachePut(value = "students", key = "#student.id")
    public Student updateStudent(Student student) {
        // Simulating database update
        System.out.println("Updating student with id=" + student.getId() + " in database.");
        return studentRepository.save(student);
    }

    @CacheEvict(value = "students", key = "#id")
    public void deleteStudent(Long id) {
        // Simulating database delete
        System.out.println("Deleting student with id=" + id + " from database.");
        studentRepository.deleteById(id);
    }
}
