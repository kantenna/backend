package com.example.jpa.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa.entity.Team;
import com.example.jpa.entity.TeamMember;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@SpringBootTest
public class TeamRepositoryTest {
    
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Test
    public void insertTest(){

        Team team = Team.builder().name("team1").build();
        teamRepository.save(team);

        TeamMember member = TeamMember.builder().name("홍길동").team(team).build();
        teamMemberRepository.save(member);
    }

    // 기존 팀에 인원을 추가하기
    @Test
    @Commit
    public void insertTest2(){

        // Team team = Team.builder().id(1L).build();
        Team team = teamRepository.findById(3L).get();

        TeamMember member = TeamMember.builder().name("성춘향").team(team).build();
        teamMemberRepository.save(member);
    }

    @Test
    public void insertTest3(){

        Team team = Team.builder().name("team3").build();
        teamRepository.save(team);

    }

    @Test
    public void readTest(){

        // Team team = Team.builder().id(1L).build();
        Team team = teamRepository.findById(1L).get(); // Team(id=1, name=team1)
        System.out.println(team); // Team(id=1, name=null)

        // 외래키가 적용된 테이블이기 때문에 join을 바로 해서 코드 실행
        TeamMember member = teamMemberRepository.findById(1L).get();
        System.out.println(member);
        
        // 팀원 => 팀 조회
        // System.out.println("팀 명 : " + member.getTeam().getName());

        // 팀 => 팀원 조회 (X)
    
    }

    @Transactional
    @Test
    public void readTest4(){

        TeamMember member = teamMemberRepository.findById(4L).get();
        System.out.println(member);
        System.out.println(member.getTeam());
    }

    @Test
    public void updateTest(){

        // 팀 이름 변경
        Team team = teamRepository.findById(1L).get();
        team.changeName("플라워");
        System.out.println(teamRepository.save(team));

        // 팀 변경
        TeamMember teamMember = teamMemberRepository.findById(2L).get();
        teamMember.changeTeam(Team.builder().id(2L).build());
        System.out.println(teamMemberRepository.save(teamMember));
    }

    @Test
    public void deleteTest(){
        
        // 1. 팀원 삭제
        // 2. 삭제하려고 하는 팀의 팀원들 팀변경 !!!!!!

        // 팀원(팀 정보를 이용해) 찾기
        List<TeamMember> result = teamMemberRepository.findByTeam(Team.builder().id(1L).build());
        result.forEach(m -> {
            m.changeTeam(Team.builder().id(2L).build());
            teamMemberRepository.save(m);
        });


        // 팀 삭제
        teamRepository.deleteById(1L);
    }

    @Test
    public void deleteTest2(){
        
        // 1. 팀원 삭제 !!!!!!!
        // 2. 삭제하려고 하는 팀의 팀원들 팀변경

        // 팀원(팀 정보를 이용해) 찾기
        List<TeamMember> result = teamMemberRepository.findByTeam(Team.builder().id(2L).build());
        result.forEach(m -> {
            teamMemberRepository.delete(m);
        });


        // 팀 삭제
        teamRepository.deleteById(2L);
    }

    // ------------------------- 팀 => 멤버 조회

    @Transactional
    @Test
    public void readTest2(){

        Team team = teamRepository.findById(3L).get();
        
        // 팀 => 팀원 조회
        // @OneToMany(mappedBy = "team")
        System.out.println(team); // select 
        System.out.println(team.getMembers());
        // System.out.println(team.getMembers()); // select
    }

    @Test
    public void readTest3(){

        Team team = teamRepository.findById(3L).get();
        
        // 팀 => 팀원 조회(left join 처리)
        // @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
        System.out.println(team);
        // System.out.println(team.getMembers());
    }

    // cascade 개념 적용
    @Test
    public void insertCascadeTest(){

        Team team = Team.builder().name("new").build();

        team.getMembers().add(TeamMember.builder().name("강감찬").team(team).build());
        teamRepository.save(team);
    }

    @Test
    public void removeCascadeTest(){

        teamRepository.deleteById(4L);
    }

    @Commit
    @Transactional
    @Test
    public void removeOrphanTest(){

        Team team = teamRepository.findById(3L).get();
        team.getMembers().remove(0);
        teamRepository.save(team);
    }

    @Commit
    @Transactional
    @Test
    public void updateCascadeTest(){

        Team team = teamRepository.findById(5L).get();
        team.changeName("sunflower");

        TeamMember teamMember = team.getMembers().get(0);
        teamMember.changeName("홍시루");

        // teamRepository.save(team);
        // dirty checking은 save 없이도 변화 감지
    }

    // @Query 테스트
    @Transactional
    @Test
    public void testQuery(){
        Team team = teamRepository.findById(3L).get();
        List<Object[]> result = teamMemberRepository.findByMemberAndTeam(team);

        for (Object[] objects : result) {
            // System.out.println(Arrays.toString(objects));
            TeamMember member = (TeamMember) objects[0];
            Team team1 = (Team) objects[1];
            System.out.println(member);
            System.out.println(team1);
        }
    }

    @Transactional
    @Test
    public void testQuery2(){

        List<Object[]> result = teamMemberRepository.findByMemberAndTeam2(3L);

        for (Object[] objects : result) {
            // System.out.println(Arrays.toString(objects));
            TeamMember member = (TeamMember) objects[0];
            Team team1 = (Team) objects[1];
            System.out.println(member);
            System.out.println(team1);
        }
    }

    @Transactional
    @Test
    public void testQuery3(){

        List<Object[]> result = teamMemberRepository.findByMemberAndTeam3();

        for (Object[] objects : result) {
            // System.out.println(Arrays.toString(objects));
            TeamMember member = (TeamMember) objects[0];
            Team team1 = (Team) objects[1];
            System.out.println(member);
            System.out.println(team1);
        }
    }
}
