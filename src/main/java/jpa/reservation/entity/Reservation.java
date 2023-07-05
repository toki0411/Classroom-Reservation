package jpa.reservation.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int numberOfMember;  //인원수
    //private Date reservationDate;
    private LocalDateTime startTime;  //예약 시작 시간
    private LocalDateTime endTime;  //예약 끝 시간

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;  //예약 상태 [예약됨, 취소]

    private LocalDateTime createdDate;

    //==연관관계 메서드==// 양방향으로 관계를 맺어준다.

    public void setClassroom(Classroom classroom){
        this.classroom = classroom;
        classroom.setReservation(this);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getReservations().add(this);
    }

    //==생성 메서드==//
    public static Reservation createReservation(Member member, Classroom classroom, LocalDateTime startTime, LocalDateTime endTime, Integer numberOfMember){
        Reservation reservation = new Reservation();
        reservation.setMember(member);
        reservation.setClassroom(classroom);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setNumberOfMember(numberOfMember);
        reservation.setCreatedDate(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.RESERVATED);
        return reservation;
    }


}
