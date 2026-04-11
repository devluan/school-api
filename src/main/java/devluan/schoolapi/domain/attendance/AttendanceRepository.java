package devluan.schoolapi.domain.attendance;

import devluan.schoolapi.domain.classroom.Classroom;
import devluan.schoolapi.domain.lesson.Lesson;
import devluan.schoolapi.domain.student.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

    Optional<Attendance> findAttendancesByLessonAndStudent(Lesson lesson, Student student);

    Page<Attendance> findAttendancesByStudent(Student student, Pageable pageable);

    Page<Attendance> findAttendancesByLesson(Lesson lesson, Pageable pageable);

    Page<Attendance> findAttendancesByStudentClassroom(Classroom classroom, Pageable pageable);

    long countAttendancesByStatus(AttendanceStatus status);

    long countAttendancesByStudentAndStatus(Student student, AttendanceStatus status);

    long countAttendancesByStudentClassroomAndStatus(Classroom classroom, AttendanceStatus status);

    long countAttendancesByLessonAndStatus(Lesson lesson, AttendanceStatus status);

    long countAttendancesByLessonDateAndStatus(LocalDate lessonDate, AttendanceStatus status);
}