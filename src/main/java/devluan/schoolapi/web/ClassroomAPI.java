package devluan.schoolapi.web;

import devluan.schoolapi.domain.classroom.Classroom;
import devluan.schoolapi.domain.classroom.ClassroomService;
import devluan.schoolapi.domain.student.StudentService;
import devluan.schoolapi.web.input.ClassroomInput;
import devluan.schoolapi.web.mapping.ClassroomMapper;
import devluan.schoolapi.web.mapping.StudentMapper;
import devluan.schoolapi.web.output.ClassroomOutput;
import devluan.schoolapi.web.output.StudentOutput;
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
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "classrooms")
@RequiredArgsConstructor
public class ClassroomAPI {
    private final ClassroomService classroomService;
    private final ClassroomMapper classroomMapper;
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @GetMapping
    public ResponseEntity<Page<EntityModel<ClassroomOutput>>> listClassrooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(classroomMapper.map(classroomService.listClassroom(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ClassroomOutput>> findClassroom(
            @PathVariable UUID id
    ) {
        Classroom classroom = classroomService.findClassroom(id);
        return ResponseEntity.ok(classroomMapper.buildOutputModel(classroom));
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Page<EntityModel<StudentOutput>>> findStudentsByClassroom(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Classroom classroom = classroomService.findClassroom(id);
        return ResponseEntity.ok(studentMapper.map(studentService.findStudentsByClassroom(classroom, pageable)));
    }

    @PostMapping
    public ResponseEntity<EntityModel<ClassroomOutput>> createClassroom(
            @RequestBody ClassroomInput input
    ) {
        Classroom classroom = classroomService.createClassroom(classroomMapper.map(input));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(classroomMapper.buildOutputModel(classroom));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ClassroomOutput>> updateClassroom(
            @PathVariable UUID id,
            @RequestBody ClassroomInput input
    ) {
        Classroom classroom = classroomService.updateClassroom(id, classroomMapper.map(input));
        return ResponseEntity.ok(classroomMapper.buildOutputModel(classroom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassroom(
            @PathVariable UUID id
    ) {
        classroomService.deleteClassroom(id);
        return ResponseEntity.noContent().build();
    }
}