package devluan.schoolapi.web;

import devluan.schoolapi.domain.attendance.AttendancesResume;
import devluan.schoolapi.domain.attendance.AttendanceService;
import devluan.schoolapi.domain.student.Student;
import devluan.schoolapi.domain.student.StudentService;
import devluan.schoolapi.web.input.StudentInput;
import devluan.schoolapi.web.mapping.AttendanceMapper;
import devluan.schoolapi.web.mapping.StudentMapper;
import devluan.schoolapi.web.output.AttendanceOutput;
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
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/students")
@RequiredArgsConstructor
public class StudentAPI {
    private final StudentService studentService;
    private final StudentMapper studentMapper;
    private final AttendanceService attendanceService;
    private final AttendanceMapper attendanceMapper;

    @GetMapping
    public ResponseEntity<Page<EntityModel<StudentOutput>>> listStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(studentMapper.map(studentService.listStudents(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<StudentOutput>> findStudent(
            @PathVariable UUID id) {
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(studentMapper.buildOutputModel(student));
    }

    @GetMapping("/{id}/attendances")
    public ResponseEntity<Page<EntityModel<AttendanceOutput>>> listAttendancesByStudents(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(attendanceMapper.map(attendanceService.listAttendancesByStudent(student, pageable)));
    }

    @GetMapping("/{id}/attendances/resume")
    public ResponseEntity<AttendancesResume> getAttendancesResumeByStudent(
            @PathVariable UUID id
    ) {
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(attendanceService.getResumeByStudent(student));
    }

    @PostMapping
    public ResponseEntity<EntityModel<StudentOutput>> createStudent(
            @RequestBody StudentInput input) {
        Student student = studentService.createStudent(studentMapper.map(input));
        return ResponseEntity.status(HttpStatus.CREATED).body(studentMapper.buildOutputModel(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<StudentOutput>> updateStudent(
            @PathVariable UUID id,
            @RequestBody StudentInput input) {
        Student student = studentService.updateStudent(id, studentMapper.map(input));
        return ResponseEntity.ok(studentMapper.buildOutputModel(student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable UUID id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}