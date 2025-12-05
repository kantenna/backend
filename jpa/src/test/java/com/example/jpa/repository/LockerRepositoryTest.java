package com.example.jpa.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Locker;
import com.example.jpa.entity.SportsMember;

import jakarta.transaction.Transactional;

@SpringBootTest
public class LockerRepositoryTest {
    
    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private SportsMemberRepository sportsMemberRepository;

    @Transactional
    @Test
    public void readTest(){
        // 회원 조회
        SportsMember member = sportsMemberRepository.findById(2L).get();
        System.out.println(member);
        // 사물함 조회
        System.out.println(member.getLocker().getName());
    }

    @Test
    public void readTest2(){
        // 전체 회원 조회
        sportsMemberRepository.findAll().forEach(m -> {
            // 회원 정보
            System.out.println(m);
            // 사물함 정보
            System.out.println(m.getLocker());
        });      
    }

    @Test
    public void readTest3(){
        // 사물함 => 회원 조회
        Locker locker = lockerRepository.findById(2L).get();
        System.out.println(locker);
        // 회원 조회
        System.out.println(locker.getSportsMember().getName());
    }

    @Test
    public void readTest4(){
        // 전체 사물함 조회
        lockerRepository.findAll().forEach(locker -> {
            // 사물함 정보
            System.out.println(locker);
            // 회원 정보
            // System.out.println(locker.getSportsMember());
        });      
    }

    @Test
    public void insertTest(){

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Locker locker = Locker.builder().name("locker"+i).build();
            SportsMember sportsMember = SportsMember.builder().name("user"+i).locker(locker).build();

            lockerRepository.save(locker);
            sportsMemberRepository.save(sportsMember);
        });
    }

    @Test
    public void deleteTest(){
        // 외래키 변경 or 자식 삭제 후 부모 삭제
        sportsMemberRepository.deleteById(1L);
        lockerRepository.deleteById(1L);
    }
}
