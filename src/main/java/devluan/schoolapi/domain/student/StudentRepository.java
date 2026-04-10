package devluan.schoolapi.domain.student;

import devluan.schoolapi.domain.classroom.Classroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    Page<Student> findStudentsByClassroom(Classroom classroom, Pageable pageable);
}