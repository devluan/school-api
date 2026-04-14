package devluan.schoolapi.domain.student;

import devluan.schoolapi.domain.classroom.Classroom;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentAttendanceRateRepository studentAttendanceRateRepository;

    public Page<StudentAttendanceRate> listStudents(Double minFreq, Double maxFreq, Pageable pageable) {
        return studentAttendanceRateRepository.findAllByAttendanceRateBetween(minFreq, maxFreq, pageable);
    }

    public Student findStudent(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    public Page<Student> findStudentsByClassroom(Classroom classroom, Pageable pageable) {
        return studentRepository.findStudentsByClassroom(classroom, pageable);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(UUID id, Student updated) {
        Student student = findStudent(id);
        student.setName(updated.getName());
        student.setBirthDate(updated.getBirthDate());
        student.setClassroom(updated.getClassroom());
        return studentRepository.save(student);
    }

    public void deleteStudent(UUID id) {
        Student student = findStudent(id);
        studentRepository.delete(student);
    }
}