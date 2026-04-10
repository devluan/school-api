package devluan.schoolapi.web.output;

import devluan.schoolapi.domain.attendance.AttendanceStatus;

import java.util.UUID;

public record AttendanceOutput(
        UUID id,
        UUID lessonId,
        UUID studentId,
        AttendanceStatus status
) {
}
