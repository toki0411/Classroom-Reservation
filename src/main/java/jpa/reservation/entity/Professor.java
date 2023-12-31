package jpa.reservation.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@DiscriminatorValue("P")
@Getter
@Setter
public class Professor extends Member {

    private Long professorNumber;
}
