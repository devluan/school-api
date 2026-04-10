package devluan.schoolapi.domain.classroom;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClassroomService {
    private final ClassroomRepository classroomRepository;

    public Page<Classroom> listClassroom(Pageable pageable) {
        return classroomRepository.findAllByRemovedFalse(pageable);
    }

    public Classroom findClassroom(UUID id) {
        return classroomRepository
                .findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Classroom not found"));
    }

    public Classroom createClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    public Classroom updateClassroom(UUID id, Classroom updated) {
        Classroom classroom = findClassroom(id);
        classroom.setGrade(updated.getGrade());
        classroom.setLetter(updated.getLetter());
        classroom.setShift(updated.getShift());
        return classroom;
    }

    public void deleteClassroom(UUID id) {
        Classroom classroom = classroomRepository.getReferenceById(id);
        classroom.setRemoved(true);
        classroomRepository.save(classroom);
    }
}