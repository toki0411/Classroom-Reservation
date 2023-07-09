package jpa.reservation.service;

import jpa.reservation.entity.Member;
import jpa.reservation.entity.Professor;
import jpa.reservation.entity.Student;
import jpa.reservation.repository.MemberRepository;
import jpa.reservation.web.LoginForm;
import jpa.reservation.web.ProfessorForm;
import jpa.reservation.web.StudentForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //학생 회원 가입
    @Transactional
    public void joinStudent(StudentForm form) {
        Student student = new Student();
        student.setEmail(form.getEmail());
        student.setPassword(form.getPassword());
        student.setName(form.getName());
        student.setMajor(form.getMajor());
        student.setStudentNumber(form.getStudentNumber());

        validateDuplicateMember(student);
        memberRepository.save(student);
    }
    //학생 회원 가입
    @Transactional
    public void joinProfessor(ProfessorForm form) {
        Professor professor = new Professor();
        professor.setEmail(form.getEmail());
        professor.setPassword(form.getPassword());
        professor.setName(form.getName());
        professor.setMajor(form.getMajor());
        professor.setProfessorNumber(form.getProfessorNumber());

        validateDuplicateMember(professor);
        memberRepository.save(professor);
    }


    //중복 조회
    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //전체 학생 회원 조회
    public Result findStudents() {
        List<Student> findStudents = memberRepository.findAllStudents();
        List<StudentDto> collect = findStudents.stream()
                .map(m -> new StudentDto(m.getId(), m.getName(), m.getStudentNumber(), m.getMajor(), m.getEmail()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    //전체 교수 회원 조회
    public Result findProfessors() {
        List<Professor> findProfessors = memberRepository.findAllProfessors();
        List<ProfessorDto> collect = findProfessors.stream()
                .map(m -> new ProfessorDto(m.getId(), m.getName(), m.getProfessorNumber(), m.getMajor(), m.getEmail()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

//    public Member findOne(Long memberId) {
//        return memberRepository.findOne(memberId);
//    }
    //전체 회원의 이름과 id만 조회
    public Result findMembers() {
        List<Member> findMembers = memberRepository.findAll();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getId(), m.getName()))
                .collect(Collectors.toList());
        return new Result(collect);
    }
    //로그인
    public boolean login(LoginForm form) {
        Member findMember = memberRepository.findByEmail(form.getEmail());
        if(findMember == null) {
            return false;
        }
        if(!findMember.getPassword().equals(form.getPassword())){
            return false;
        }
        return true;
    }

    @Data
    @AllArgsConstructor
    public static class Result<T> {
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class StudentDto {
        private Long id;
        private String name;
        private Long studentNumber;
        private String major;
        private String email;
    }
    @Data
    @AllArgsConstructor
    static class ProfessorDto {
        private Long id;
        private String name;
        private Long professorNumber;
        private String major;
        private String email;
    }
    @Data
    @AllArgsConstructor
    static class MemberDto {
        private Long id;
        private String name;
    }

}
