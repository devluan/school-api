package devluan.schoolapi.domain.lesson;

import devluan.schoolapi.domain.attendance.Attendance;
import devluan.schoolapi.domain.attendance.AttendanceService;
import devluan.schoolapi.domain.classroom.Classroom;
import devluan.schoolapi.domain.teacher.Teacher;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final AttendanceService attendanceService;

    public Page<Lesson> listLessons(Pageable pageable) {
        return lessonRepository.findAll(pageable);
    }

    public Lesson findLesson(UUID id) {
        return lessonRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
    }

    public Page<Lesson> listLessonsByClassroom(Classroom classroom,Pageable pageable) {
        return lessonRepository.findLessonsByClassroom(classroom, pageable);
    }

    public Page<Lesson> listLessonsByTeacher(Teacher teacher, Pageable pageable) {
        return lessonRepository.findLessonsByTeacher(teacher, pageable);
    }

    public Lesson createLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Lesson updateLesson(UUID id, Lesson updated) {
        Lesson lesson = findLesson(id);
        lesson.setClassroom(updated.getClassroom());
        lesson.setTeacher(updated.getTeacher());
        for (Attendance att : lesson.getAttendances()) {
            attendanceService.validateStudentInLessonClassroom(att);
        }
        return lessonRepository.save(lesson);
    }

    public void deleteLesson(UUID id) {
        Lesson lesson = findLesson(id);
        lessonRepository.delete(lesson);
    }
}