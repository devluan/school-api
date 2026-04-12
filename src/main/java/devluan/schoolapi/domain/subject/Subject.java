package devluan.schoolapi.domain.subject;

import devluan.schoolapi.domain.teacher.Teacher;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "tb_subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @OneToMany(mappedBy = "subject")
    private Set<Teacher> teachers = new HashSet<>();
    private Boolean removed = false;
}