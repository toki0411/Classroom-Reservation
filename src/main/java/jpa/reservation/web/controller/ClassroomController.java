package jpa.reservation.web.controller;

import jpa.reservation.entity.Classroom;
import jpa.reservation.service.ClassroomService;
import jpa.reservation.service.ReservationService;
import jpa.reservation.web.ClassroomForm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClassroomController {
    private final ClassroomService classroomService;

    @GetMapping(value = "/classroom/new")
    public String createReservation(Model model) {
        model.addAttribute("form", new ClassroomForm());
        return "classroom/createClassroomForm";
    }

    @PostMapping(value = "/classroom/new")
    public String create(ClassroomForm form) {
        classroomService.saveClassroom(form);
        return "redirect:/classroom/list";
    }

    @GetMapping(value = "/classroom/list")
    public String list(Model model) {
        ClassroomService.Result classrooms = classroomService.findClassrooms();
        model.addAttribute("classrooms", classrooms);
        return "classroom/classroomList";
    }
}
