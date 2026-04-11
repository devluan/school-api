package devluan.schoolapi.web;

import devluan.schoolapi.domain.attendance.Attendance;
import devluan.schoolapi.domain.attendance.AttendancesResume;
import devluan.schoolapi.domain.attendance.AttendanceService;
import devluan.schoolapi.web.input.AttendanceInput;
import devluan.schoolapi.web.mapping.AttendanceMapper;
import devluan.schoolapi.web.output.AttendanceOutput;
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
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/attendances")
public class AttendanceAPI {
    private final AttendanceService attendanceService;
    private final AttendanceMapper attendanceMapper;

    @GetMapping
    public ResponseEntity<Page<EntityModel<AttendanceOutput>>> listAttendances(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(attendanceMapper.map(attendanceService.listAttendances(pageable)));
    }

    @GetMapping("/resume")
    public ResponseEntity<AttendancesResume> getResume() {
        return ResponseEntity.ok(attendanceService.getResume());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AttendanceOutput>> findAttendance(
            @PathVariable UUID id
    ) {
        Attendance attendance = attendanceService.findAttendance(id);
        return ResponseEntity.ok(attendanceMapper.buildOutputModel(attendanceService.createAttendance(attendance)));
    }

    @PostMapping
    public ResponseEntity<EntityModel<AttendanceOutput>> createAttendance(
            @RequestBody AttendanceInput input
    ) {
        Attendance attendance = attendanceService.createAttendance(attendanceMapper.map(input));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(attendanceMapper.buildOutputModel(attendance));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<AttendanceOutput>> updateAttendance(
            @PathVariable UUID id,
            @RequestBody AttendanceInput input
    ) {
        Attendance attendance = attendanceService.updateAttendance(id, attendanceMapper.map(input));
        return ResponseEntity.ok(attendanceMapper.buildOutputModel(attendance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(
            @PathVariable UUID id
    ) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}