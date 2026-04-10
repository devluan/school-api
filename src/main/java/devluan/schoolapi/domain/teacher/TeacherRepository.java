package devluan.schoolapi.domain.teacher;

import devluan.schoolapi.domain.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    Optional<Teacher> findByIdAndRemovedFalse(UUID id);

    Page<Teacher> findAllByRemovedFalse(Pageable pageable);

    Page<Teacher> findTeachersBySubject(Subject subject, Pageable pageable);
}