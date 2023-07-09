package jpa.reservation.web;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClassroomForm {
    private String address;
    private int limitedMember;

}
