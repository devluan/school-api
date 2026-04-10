package devluan.schoolapi.web.output;

import java.time.LocalDate;
import java.util.UUID;

public record LessonOutput(
        UUID id,
        UUID classroomId,
        UUID teacherId,
        LocalDate date
) {
}