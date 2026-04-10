package devluan.schoolapi.domain.attendance;

import devluan.schoolapi.domain.lesson.Lesson;
import devluan.schoolapi.domain.student.Student;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "tb_attendances")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}