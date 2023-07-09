package jpa.reservation;

import jpa.reservation.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

//샘플 데이터
@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
        initService.dbInit3();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = createStudent("신컴공", "abcde@donga.ac.kr", "1234", "컴퓨터공학", 1823411L);
            em.persist(member);

            Classroom classroom = createClassroom("S06-601", 4);
            em.persist(classroom);

            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime endTime = LocalDateTime.now();

            Reservation reservation = Reservation.createReservation(member, classroom, startTime, endTime,2);
            em.persist(reservation);
        }

        public void dbInit2() {
            Member member = createStudent("박전자", "fghijk@donga.ac.kr", "5678", "전자공학", 2017425L);
            em.persist(member);

            Classroom classroom = createClassroom("S06-402", 8);
            em.persist(classroom);

            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime endTime = LocalDateTime.now();

            Reservation reservation = Reservation.createReservation(member, classroom, startTime, endTime,4);
            em.persist(reservation);
        }

        public void dbInit3() {
            Member member = createProfessor("김소재", "lmnop@donga.ac.kr", "9101112", "신소재공학", 2311201L);
            em.persist(member);

            Classroom classroom = createClassroom("S05-423", 4);
            em.persist(classroom);

            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime endTime = LocalDateTime.now();

            Reservation reservation = Reservation.createReservation(member, classroom, startTime, endTime,2);
            em.persist(reservation);
        }

        private Member createStudent(String name, String email, String password, String major, Long studentNumber) {
            Student student = new Student();
            student.setName(name);
            student.setEmail(email);
            student.setPassword(password);
            student.setMajor(major);
            student.setStudentNumber(studentNumber);
            return student;
        }
        private Member createProfessor(String name, String email, String password, String major, Long professorNumber) {
            Professor professor = new Professor();
            professor.setName(name);
            professor.setEmail(email);
            professor.setPassword(password);
            professor.setMajor(major);
            professor.setProfessorNumber(professorNumber);
            return professor;
        }

        private Classroom createClassroom(String address, int limitedMember) {
            Classroom classroom = new Classroom();
            classroom.setAddress(address);
            classroom.setLimitedMember(limitedMember);
            return classroom;
        }
    }
}
