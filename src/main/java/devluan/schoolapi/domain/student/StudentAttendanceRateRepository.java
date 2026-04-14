package devluan.schoolapi.domain.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentAttendanceRateRepository extends JpaRepository<StudentAttendanceRate, UUID> {
    Page<StudentAttendanceRate> findAllByAttendanceRateBetween(Double min, Double max, Pageable pageable);
}