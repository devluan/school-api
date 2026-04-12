package devluan.schoolapi.domain.classroom;

import devluan.schoolapi.domain.lesson.Lesson;
import devluan.schoolapi.domain.student.Student;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "tb_classrooms")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Integer grade;
    private Character letter;
    @Enumerated(EnumType.STRING)
    private Shift shift;
    @OneToMany(mappedBy = "classroom")
    private Set<Student> students = new HashSet<>();
    @OneToMany(mappedBy = "classroom")
    private Set<Lesson> lessons = new HashSet<>();
    private Boolean removed = false;

    public String getName() {
        return String.format("%dth Grade, Class %s", grade, letter);
    }
}