package jpa.reservation.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginForm {

    @NotEmpty(message = "이메일은 필수 입니다")
    private String email;
    private String password;
}
