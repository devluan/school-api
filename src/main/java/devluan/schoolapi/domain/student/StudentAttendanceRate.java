package devluan.schoolapi.domain.student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "vw_students_attendance_rate")
@Immutable
public class StudentAttendanceRate {
    @Id
    private UUID id;
    private String name;
    private LocalDate birthDate;
    private UUID classroomId;
    private Double attendanceRate;
}
