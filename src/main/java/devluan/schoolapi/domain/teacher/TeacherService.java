package devluan.schoolapi.domain.teacher;

import devluan.schoolapi.domain.subject.Subject;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public Page<Teacher> listTeachers(Pageable pageable) {
        return teacherRepository.findAllByRemovedFalse(pageable);
    }

    public Teacher findTeacher(UUID id) {
        return teacherRepository
                .findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
    }

    public Page<Teacher> findTeachersBySubject(Subject subject, Pageable pageable) {
        return teacherRepository.findTeachersBySubject(subject, pageable);
    }

    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher updateSubject(UUID id, Teacher updated) {
        Teacher teacher = findTeacher(id);
        teacher.setName(updated.getName());
        teacher.setSubject(updated.getSubject());
        return teacher;
    }

    public void deleteSubject(UUID id) {
        Teacher teacher = teacherRepository.getReferenceById(id);
        teacher.setRemoved(true);
        teacherRepository.save(teacher);
    }
}
