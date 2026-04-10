package devluan.schoolapi.domain.lesson;

import devluan.schoolapi.domain.classroom.Classroom;
import devluan.schoolapi.domain.teacher.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    Page<Lesson> findLessonsByClassroom(Classroom classroom, Pageable pageable);

    Page<Lesson> findLessonsByTeacher(Teacher teacher, Pageable pageable);
}