package com.example.student.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.student.entity.Student;
import com.example.student.entity.constant.Grade;

@Disabled
@SpringBootTest
public class StudentRepositoryTest {

    @Autowired    
    private StudentRepository studentRepository;

    @Test
    public void deleteTest(){
        // Student student = studentRepository.findById(1L).get();
        // studentRepository.delete(student);
        studentRepository.deleteById(1L);
    }

    @Test
    public void reatTest(){
        // ID값으로 조회
        Student student = studentRepository.findById(2L).get();
        System.out.println(student);
    }

    @Test
    public void reatTest2(){
        // 전체 학생 조회
        List<Student> students = studentRepository.findAll();

        for (Student student : students) {
            System.out.println(student);
        }
    }

    @Test
    public void updateTest(){
        // Entity
        // update student set 수정컬럼=값 where id=1;

        Optional<Student> result = studentRepository.findById(1L);
        
        Student student = result.get();
        student.changeGrade(Grade.FRESHMAN);

        // insert(c), update(u) 작업 시 호출
        studentRepository.save(student);
    }

    @Test
    public void insertTest(){
        for (int i = 1; i < 11; i++) {
            Student student = Student.builder()
            .name("성춘향" + i)
            .addr("서울")
            .gender("F")
            .grade(Grade.SENIOR)
            .build();
            
            studentRepository.save(student);
        }
    }
}
