package jpa.reservation.service;

import jpa.reservation.entity.*;
import jpa.reservation.repository.ClassroomRepository;
import jpa.reservation.repository.MemberRepository;
import jpa.reservation.repository.ReservationRepository;
import jpa.reservation.web.ReservationForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final ClassroomRepository classroomRepository;

    //예약 기능
    @Transactional
    public void reservation(ReservationForm form) {
        Long memberId = form.getMemberId();
        Long classroomId = form.getClassroomId();
        String startTimeString = form.getStartTime();
        String endTimeString = form.getEndTime();
        LocalDateTime startTime = LocalDateTime.parse(startTimeString);
        LocalDateTime endTime = LocalDateTime.parse(endTimeString);
        int numberOfMember = form.getNumberOfMember();

        Member member = memberRepository.findOne(memberId);
        Classroom classroom = classroomRepository.findOne(classroomId);

        Reservation reservation = Reservation.createReservation(member, classroom, startTime, endTime, numberOfMember);
        reservationRepository.save(reservation);
    }

    //전체 예약 조회
    public Result findReservations(ReservationSearch reservationSearch) {
        List<Reservation> findReservations = reservationRepository.findAll(reservationSearch);
        List<ReservationDto> collect = findReservations.stream()
                .map(r -> new ReservationService.ReservationDto(r.getId(), r.getMember().getName(),
                        r.getMember().getEmail(), r.getClassroom().getAddress(), r.getStartTime(), r.getEndTime(),
                        r.getNumberOfMember(), r.getStatus(), r.getCreatedDate()))
                .collect(Collectors.toList());
        return new ReservationService.Result(collect);
    }

    //예약 하나 조회
    public Result findReservation(Long reservationId) {
        Reservation findReservation = reservationRepository.findOne(reservationId);
        ReservationFormDto reservationFormDto = new ReservationFormDto(
                findReservation.getId(),
                findReservation.getMember().getId(),
                findReservation.getMember().getName(),
                findReservation.getClassroom().getId(),
                findReservation.getClassroom().getAddress(),
                findReservation.getStartTime(),
                findReservation.getEndTime(),
                findReservation.getNumberOfMember()
        );
        return new ReservationService.Result(reservationFormDto);
    }

    @Transactional
    public void update(Long reservationId, ReservationForm form){
        Reservation reservation = reservationRepository.findOne(reservationId);
        Member member = memberRepository.findOne(form.getMemberId());
        Classroom classroom = classroomRepository.findOne(form.getClassroomId());

        reservation.setMember(member);
        reservation.setClassroom(classroom);

        String startTimeString = form.getStartTime();
        String endTimeString = form.getEndTime();
        LocalDateTime startTime = LocalDateTime.parse(startTimeString);
        LocalDateTime endTime = LocalDateTime.parse(endTimeString);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setNumberOfMember(form.getNumberOfMember());
    }

    //예약 취소
    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findOne(reservationId);
        reservationRepository.cancel(reservation);
    }


    @Data
    @AllArgsConstructor
    public static class Result<T> {
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class ReservationDto {
        private Long id;
        private String memberName;
        private String memberEmail;
        private String classroomAddress;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private int numberOfMember;
        private ReservationStatus status;
        private LocalDateTime createdDate;
    }

    @Data
    @AllArgsConstructor
    static class ReservationFormDto {
        private Long id;
        private Long memberId;
        private String memberName;
        private Long classroomId;
        private String classroomAddress;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private int numberOfMember;
    }

}
