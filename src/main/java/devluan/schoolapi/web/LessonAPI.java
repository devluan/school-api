package devluan.schoolapi.web;

import devluan.schoolapi.domain.attendance.AttendancesResume;
import devluan.schoolapi.domain.attendance.AttendanceService;
import devluan.schoolapi.domain.lesson.Lesson;
import devluan.schoolapi.domain.lesson.LessonService;
import devluan.schoolapi.web.input.LessonInput;
import devluan.schoolapi.web.mapping.AttendanceMapper;
import devluan.schoolapi.web.mapping.LessonMapper;
import devluan.schoolapi.web.output.AttendanceOutput;
import devluan.schoolapi.web.output.LessonOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/lessons")
@RequiredArgsConstructor
public class LessonAPI {
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;
    private final AttendanceService attendanceService;
    private final AttendanceMapper attendanceMapper;

    @GetMapping
    public ResponseEntity<Page<EntityModel<LessonOutput>>> listLessons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(lessonMapper.map(lessonService.listLessons(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<LessonOutput>> findLesson(
            @PathVariable UUID id) {
        return ResponseEntity.ok(lessonMapper.buildOutputModel(lessonService.findLesson(id)));
    }

    @GetMapping("/{id}/attendances")
    public ResponseEntity<Page<EntityModel<AttendanceOutput>>> findAttendanceByStudents(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Lesson lesson = lessonService.findLesson(id);
        return ResponseEntity.ok(attendanceMapper.map(attendanceService.findAttendancesByLesson(lesson, pageable)));
    }

    @GetMapping("/{id}/attendances/resume")
    public ResponseEntity<AttendancesResume> getAttendanceResumeByLesson(
            @PathVariable UUID id
    ) {
        Lesson lesson = lessonService.findLesson(id);
        return ResponseEntity.ok(attendanceService.getResumeByLesson(lesson));
    }

    @GetMapping("/attendances/{date}/resume")
    public ResponseEntity<AttendancesResume> getAttendanceResumeByLessonDate(
            @PathVariable LocalDate date
    ) {
        return ResponseEntity.ok(attendanceService.getResumeByLessonDate(date));
    }

    @PostMapping
    public ResponseEntity<EntityModel<LessonOutput>> createLesson(
            @RequestBody LessonInput input) {
        Lesson lesson = lessonMapper.map(input);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lessonMapper.buildOutputModel(lessonService.createLesson(lesson)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<LessonOutput>> updateLesson(
            @PathVariable UUID id,
            @RequestBody LessonInput input) {
        Lesson lesson = lessonMapper.map(input);
        return ResponseEntity.ok(lessonMapper.buildOutputModel(lessonService.updateLesson(id, lesson)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(
            @PathVariable UUID id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}