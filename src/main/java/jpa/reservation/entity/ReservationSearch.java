package jpa.reservation.entity;

import jpa.reservation.service.ReservationService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationSearch {  //검색 조건 파라미터

    private String memberName;
    private ReservationStatus reservationStatus;
}
