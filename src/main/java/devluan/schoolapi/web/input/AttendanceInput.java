package devluan.schoolapi.web.input;

import devluan.schoolapi.domain.attendance.AttendanceStatus;

import java.util.UUID;

public record AttendanceInput(
        UUID lessonId,
        UUID studentId,
        AttendanceStatus status
) {
}
