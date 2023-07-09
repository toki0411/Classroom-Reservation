package jpa.reservation.web.controller;

import jpa.reservation.entity.Classroom;
import jpa.reservation.entity.Member;
import jpa.reservation.entity.ReservationSearch;
import jpa.reservation.entity.Reservation;
import jpa.reservation.service.ClassroomService;
import jpa.reservation.service.MemberService;
import jpa.reservation.service.ReservationService;
import jpa.reservation.web.ReservationForm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final MemberService memberService;
    private final ClassroomService classroomService;

    //예약 하기
    @GetMapping(value = "/reservation/new")
    public String createReservation(Model model) {
        MemberService.Result members = memberService.findMembers();
        ClassroomService.Result classrooms = classroomService.findClassrooms();
        model.addAttribute("members", members);
        model.addAttribute("classrooms", classrooms);
        return "reservation/createReservationForm";
    }

    @PostMapping(value = "/reservation/new")
    public String create(@Valid ReservationForm reservationform) {
        reservationService.reservation(reservationform);
        return "redirect:/reservation/list";
    }

    //예약 조회
    @GetMapping(value = "/reservation/list")
    public String reservationList(@ModelAttribute("reservationSearch") ReservationSearch reservationSearch, Model model) {
        ReservationService.Result reservations = reservationService.findReservations(reservationSearch);
        model.addAttribute("reservations", reservations);
        return "reservation/reservationList";
    }

    //예약 수정
    @GetMapping(value = "/reservation/{reservationId}/edit")
    public String editReservation(Model model, @PathVariable("reservationId") Long reservationId) {
        MemberService.Result members = memberService.findMembers();
        ClassroomService.Result classrooms = classroomService.findClassrooms();
        ReservationService.Result reservation = reservationService.findReservation(reservationId);

        model.addAttribute("members", members);
        model.addAttribute("classrooms", classrooms);
        model.addAttribute("reservation", reservation);
        return "reservation/editReservationForm";
    }

    //예약 수정
    @PutMapping(value = "/reservation/{reservationId}/edit")
    public String edit(@PathVariable("reservationId") Long reservationId, ReservationForm form) {
        reservationService.update(reservationId, form);
        return "redirect:/reservation/list";
    }

    //예약 취소
    @PostMapping(value = "/reservation/{reservationId}/cancel")
    public String cancelReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return "redirect:/reservation/list";
    }

}
