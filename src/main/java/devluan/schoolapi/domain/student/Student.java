package devluan.schoolapi.domain.student;

import devluan.schoolapi.domain.attendance.Attendance;
import devluan.schoolapi.domain.classroom.Classroom;
import devluan.schoolapi.domain.lesson.Lesson;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "tb_students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private LocalDate birthDate;
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;
    @OneToMany(mappedBy = "student",  cascade = CascadeType.ALL)
    private Set<Attendance> attendances = new HashSet<>();
}