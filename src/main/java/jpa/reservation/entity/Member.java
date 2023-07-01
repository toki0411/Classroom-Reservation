package jpa.reservation.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Setter @Getter
public abstract class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String Email;
    private String password;
    private String name;
    private String major;

    @OneToMany(mappedBy = "member")
    private List<Reservation> reservations = new ArrayList<>();

}
