package devluan.schoolapi.domain.classroom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {

    Optional<Classroom> findByIdAndRemovedFalse(UUID id);

    Page<Classroom> findAllByRemovedFalse(Pageable pageable);
}
