package devluan.schoolapi.domain.teacher;

import devluan.schoolapi.domain.lesson.Lesson;
import devluan.schoolapi.domain.subject.Subject;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name="tb_teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    @OneToMany(mappedBy = "teacher")
    private Set<Lesson> lessons = new HashSet<>();
    private Boolean removed = false;
}