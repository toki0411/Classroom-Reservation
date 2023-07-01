package jpa.reservation.service;

import jpa.reservation.entity.Classroom;
import jpa.reservation.repository.ClassroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    @Transactional
    public void saveClassroom(Classroom classroom){
        validateDuplicatedClassroom(classroom);
        classroomRepository.save(classroom);
    }

    private void validateDuplicatedClassroom(Classroom classroom) {
        List<Classroom> findClassrooms = classroomRepository.findByAddress(classroom.getAddress());
        if(!findClassrooms.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 강의실입니다.");
        }
    }

    public List<Classroom> findClassrooms() {
        return classroomRepository.findAll();
    }

    public Classroom findOne(Long classroomId){
        return classroomRepository.findOne(classroomId);
    }

}
