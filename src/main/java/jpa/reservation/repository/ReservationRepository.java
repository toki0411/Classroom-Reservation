package jpa.reservation.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpa.reservation.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

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
        reservation.setStatus(ReservationStatus.CANCELED);
    }

    public List<Reservation> findAll(ReservationSearch reservationSearch) {
        QReservation reservation = QReservation.reservation;
        QMember member = QMember.member;

        JPAQueryFactory query = new JPAQueryFactory(em);
        return query.select(reservation)
                .from(reservation)
                .join(reservation.member, member)
                .where(statusEq(reservationSearch.getReservationStatus()),
                        nameLike(reservationSearch.getMemberName()))
                .limit(1000)
                .fetch();
    }

    private BooleanExpression statusEq(ReservationStatus statusCond) {
        if (statusCond == null) {
            return null;
        }
        return QReservation.reservation.status.eq(statusCond);
    }

    private BooleanExpression nameLike(String nameCond) {
        if (!StringUtils.hasText(nameCond)) {
            return null;
        }
        return QMember.member.name.like(nameCond);
    }

}
