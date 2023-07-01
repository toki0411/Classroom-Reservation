package jpa.reservation.repository;

import jpa.reservation.entity.Classroom;
import jpa.reservation.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClassroomRepository {

    private final EntityManager em;

    public void save(Classroom classroom){
        em.persist(classroom);
    }

    public Classroom findOne(Long id) {
        return em.find(Classroom.class, id);
    }

    public List<Classroom> findAll() {
        return em.createQuery("select c from Classroom c",Classroom.class)
                .getResultList();
    }

    public List<Classroom> findByAddress(String address) {
        return em.createQuery("select c from Classroom c where c.address = :address", Classroom.class)
                .setParameter("address",address)
                .getResultList();
    }
}
