package jpa.reservation.service;

import jpa.reservation.entity.Classroom;
import jpa.reservation.repository.ClassroomRepository;
import jpa.reservation.web.ClassroomForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    //강의실 추가
    @Transactional
    public void saveClassroom(ClassroomForm form) {
        Classroom classroom = new Classroom();
        classroom.setAddress(form.getAddress());
        classroom.setLimitedMember(form.getLimitedMember());
        validateDuplicatedClassroom(classroom);
        classroomRepository.save(classroom);
    }

    private void validateDuplicatedClassroom(Classroom classroom) {
        List<Classroom> findClassrooms = classroomRepository.findByAddress(classroom.getAddress());
        if (!findClassrooms.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 강의실입니다.");
        }
    }

    //강의실 조회
    public Result findClassrooms() {
        List<Classroom> findClassrooms = classroomRepository.findAll();
        List<ClassroomService.ClassroomDto> collect = findClassrooms.stream()
                .map(c -> new ClassroomService.ClassroomDto(c.getId(), c.getAddress(), c.getLimitedMember()))
                .collect(Collectors.toList());
        return new ClassroomService.Result(collect);
    }

    @Data
    @AllArgsConstructor
    public static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class ClassroomDto {
        private Long id;
        private String address;
        private int limitedMember;
    }
}
