package jpa.reservation.web;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationForm {
    private Long memberId;
    private Long classroomId;
    private String startTime;
    private String endTime;
    private int numberOfMember;
}

