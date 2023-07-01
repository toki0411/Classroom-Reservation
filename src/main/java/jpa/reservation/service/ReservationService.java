package jpa.reservation.service;

import jpa.reservation.entity.Classroom;
import jpa.reservation.entity.Member;
import jpa.reservation.entity.Reservation;
import jpa.reservation.entity.ReservationStatus;
import jpa.reservation.repository.ClassroomRepository;
import jpa.reservation.repository.MemberRepository;
import jpa.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final ClassroomRepository classroomRepository;

    //예약
    @Transactional
    public Long Reservation(Long memberId, Long classroomId, LocalDateTime startTime, LocalDateTime endTime, Integer numberOfMember){
        Member member = memberRepository.findOne(memberId);
        Classroom classroom = classroomRepository.findOne(classroomId);

        Reservation reservation = Reservation.createReservation(member, classroom, startTime, endTime, numberOfMember);

        reservationRepository.save(reservation);
        return reservation.getId();
    }

    //예약 취소
    @Transactional
    public void cancelReservation(Long ReservationId){
        Reservation reservation = reservationRepository.findOne(ReservationId);
        reservationRepository.cancel(reservation);
    }

    //예약 조회
}
