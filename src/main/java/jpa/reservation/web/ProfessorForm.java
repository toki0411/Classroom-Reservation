package jpa.reservation.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ProfessorForm {

    @NotEmpty(message = "이메일은 필수 입니다")
    private String email;
    private String password;
    private String name;
    private String major;
    private Long professorNumber;
}
