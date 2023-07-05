package jpa.reservation.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Classroom {
    @Id
    @GeneratedValue
    @Column(name = "classroom_id")
    private Long id;

    private String address;
    private int limitedMember;
  //  private String image;

    @OneToOne(mappedBy="classroom", fetch = FetchType.LAZY)
    private Reservation reservation;
}
