package jpa.reservation.repository;

import jpa.reservation.entity.Reservation;
import jpa.reservation.entity.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final EntityManager em;

    public void save(Reservation reservation) {
        em.persist(reservation);
    }

    public Reservation findOne(Long id) {
        return em.find(Reservation.class, id);
    }

    public void cancel(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CANCEL);
    }
}
