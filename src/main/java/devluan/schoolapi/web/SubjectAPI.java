package devluan.schoolapi.web;

import devluan.schoolapi.domain.subject.Subject;
import devluan.schoolapi.domain.subject.SubjectService;
import devluan.schoolapi.domain.teacher.TeacherService;
import devluan.schoolapi.web.input.SubjectInput;
import devluan.schoolapi.web.mapping.SubjectMapper;
import devluan.schoolapi.web.mapping.TeacherMapper;
import devluan.schoolapi.web.output.SubjectOutput;
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
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "subjects")
@RequiredArgsConstructor
public class SubjectAPI {
    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;
    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;

    @GetMapping
    public ResponseEntity<Page<EntityModel<SubjectOutput>>> listSubjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(subjectMapper.map(subjectService.listSubjects(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<SubjectOutput>> findSubject(
            @PathVariable UUID id) {
        Subject subject = subjectService.findSubject(id);
        return ResponseEntity.ok(subjectMapper.buildOutputModel(subject));
    }

    @GetMapping("/{id}/teachers")
    public ResponseEntity<Page<EntityModel<TeacherOutput>>> findTeachersBySubject(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Subject subject = subjectService.findSubject(id);
        return ResponseEntity.ok(teacherMapper.map(teacherService.findTeachersBySubject(subject, pageable)));
    }

    @PostMapping
    public ResponseEntity<EntityModel<SubjectOutput>> createSubject(
            @RequestBody SubjectInput input) {
        Subject subject = subjectService.createSubject(subjectMapper.map(input));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(subjectMapper.buildOutputModel(subject));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<SubjectOutput>> updateSubject(
            @PathVariable UUID id,
            @RequestBody SubjectInput input
    ) {
        Subject subject = subjectService.updateSubject(id, subjectMapper.map(input));
        return ResponseEntity.ok(subjectMapper.buildOutputModel(subject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable UUID id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}