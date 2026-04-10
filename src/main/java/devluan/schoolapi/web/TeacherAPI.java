package devluan.schoolapi.web;

import devluan.schoolapi.domain.teacher.Teacher;
import devluan.schoolapi.domain.teacher.TeacherService;
import devluan.schoolapi.web.input.TeacherInput;
import devluan.schoolapi.web.mapping.TeacherMapper;
import devluan.schoolapi.web.output.TeacherOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/teachers")
@RequiredArgsConstructor
public class TeacherAPI {
    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;

    @GetMapping
    public ResponseEntity<Page<EntityModel<TeacherOutput>>> listTeachers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(teacherMapper.map(teacherService.listTeachers(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TeacherOutput>> findTeacher(
            @PathVariable UUID id) {
        Teacher teacher = teacherService.findTeacher(id);
        return ResponseEntity.ok(teacherMapper.buildOutputModel(teacher));
    }

    @PostMapping
    public ResponseEntity<EntityModel<TeacherOutput>> createTeacher(
            @RequestBody TeacherInput input) {
        Teacher teacher = teacherService.createTeacher(teacherMapper.map(input));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(teacherMapper.buildOutputModel(teacher));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<TeacherOutput>> updateTeacher(
            @PathVariable UUID id,
            @RequestBody TeacherInput input
    ) {
        Teacher teacher = teacherService.updateSubject(id, teacherMapper.map(input));
        return ResponseEntity.ok(teacherMapper.buildOutputModel(teacher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(
            @PathVariable UUID id) {
        teacherService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}