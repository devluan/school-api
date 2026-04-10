package devluan.schoolapi.domain.lesson;

import devluan.schoolapi.domain.attendance.Attendance;
import devluan.schoolapi.domain.classroom.Classroom;
import devluan.schoolapi.domain.teacher.Teacher;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "tb_lessons")
public class Lesson {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private Set<Attendance> attendances = new HashSet<>();
    private LocalDate date;
}
