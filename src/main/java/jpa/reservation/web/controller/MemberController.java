package jpa.reservation.web.controller;

import jpa.reservation.entity.Member;
import jpa.reservation.entity.Professor;
import jpa.reservation.entity.Student;
import jpa.reservation.service.MemberService;
import jpa.reservation.web.LoginForm;
import jpa.reservation.web.ProfessorForm;
import jpa.reservation.web.StudentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping(value = "/student/new")
    public String createStudentForm(Model model) {
        model.addAttribute("studentForm", new StudentForm());
        return "members/createStudentForm";
    }

    @GetMapping(value = "/professor/new")
    public String createProfessorForm(Model model) {
        model.addAttribute("professorForm", new ProfessorForm());
        return "members/createProfessorForm";
    }

    //학생 회원 가입
    @PostMapping(value = "/student/new")
    public String createStudent(@Valid StudentForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createStudentForm";
        }
        memberService.joinStudent(form);
        return "redirect:/";
    }

    //교수 회원 가입
    @PostMapping(value = "/professor/new")
    public String createProfessor(@Valid ProfessorForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createProfessorForm";
        }
        memberService.joinProfessor(form);
        return "redirect:/";
    }

    //전체 회원 조회
    @GetMapping(value = "/members/list")
    public String list(Model model) {
        MemberService.Result students = memberService.findStudents();
        MemberService.Result professors = memberService.findProfessors();

        model.addAttribute("students", students);
        model.addAttribute("professors", professors);
        return "members/memberList";
    }

    //로그인
    @GetMapping(value = "/login")
    public String memberLogin(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "members/loginForm";
    }

    //학생 회원 가입
    @PostMapping(value = "/login")
    public String studentLoginId(@ModelAttribute LoginForm form) {
        if (memberService.login(form)) {
            return "redirect:/";
        }
        return "members/loginForm";
    }

}
