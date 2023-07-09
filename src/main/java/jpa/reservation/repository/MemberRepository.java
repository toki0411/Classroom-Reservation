package jpa.reservation.repository;

import jpa.reservation.entity.Member;
import jpa.reservation.entity.Professor;
import jpa.reservation.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }

    public List<Student> findAllStudents() {
        return em.createQuery("SELECT s FROM Student s WHERE s.studentNumber IS NOT NULL", Student.class).getResultList();
    }

    public List<Professor> findAllProfessors() {
        return em.createQuery("SELECT p FROM Professor p WHERE p.professorNumber IS NOT NULL", Professor.class).getResultList();
    }

    public Member findByEmail(String email) {
        try {
            return em.createQuery("select m from Member m where m.email = :email", Member.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void delete(Member member) {
        em.remove(member);
    }
}
