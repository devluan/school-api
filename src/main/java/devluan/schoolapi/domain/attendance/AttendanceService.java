package devluan.schoolapi.domain.attendance;

import devluan.schoolapi.domain.attendance.exception.StudentAlreadyHasAttendanceException;
import devluan.schoolapi.domain.attendance.exception.StudentNotInThisClassroomException;
import devluan.schoolapi.domain.lesson.Lesson;
import devluan.schoolapi.domain.student.Student;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    public Page<Attendance> listAttendances(Pageable pageable) {
        return attendanceRepository.findAll(pageable);
    }

    public Attendance findAttendance(UUID id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Attendance not found"));
    }

    public Page<Attendance> listAttendancesByStudent(Student student, Pageable pageable) {
        return attendanceRepository.findAttendancesByStudent(student, pageable);
    }

    public Page<Attendance> findAttendancesByLesson(Lesson lesson, Pageable pageable) {
        return attendanceRepository.findAttendancesByLesson(lesson, pageable);
    }

    public void validateStudentInLessonClassroom(Attendance attendance) {
        Student student = attendance.getStudent();
        Lesson lesson = attendance.getLesson();
        if (student.getClassroom() != lesson.getClassroom()) {
            throw new StudentNotInThisClassroomException(
                    String.format("%s not in %s", student.getName(), lesson.getClassroom().getName())
            );
        }
    }

    public void validateStudentHasAttendance(Attendance attendance) {
        Lesson lesson = attendance.getLesson();
        Student student = attendance.getStudent();
        attendanceRepository
                .findAttendancesByLessonAndStudent(lesson, student)
                .ifPresent((att) -> {
                    throw new StudentAlreadyHasAttendanceException(
                            String.format("The student already has attendance, id: %s", att.getId())
                    );
                });
    }

    public Attendance createAttendance(Attendance attendance) {
        validateStudentInLessonClassroom(attendance);
        validateStudentHasAttendance(attendance);
        return attendanceRepository.save(attendance);
    }

    public Attendance updateAttendance(UUID id, Attendance updated) {
        Attendance attendance = findAttendance(id);
        attendance.setLesson(updated.getLesson());
        attendance.setStudent(updated.getStudent());
        attendance.setStatus(updated.getStatus());
        validateStudentInLessonClassroom(attendance);
        validateStudentHasAttendance(attendance);
        return attendanceRepository.save(attendance);
    }

    public void deleteAttendance(UUID id) {
        attendanceRepository.delete(findAttendance(id));
    }
}