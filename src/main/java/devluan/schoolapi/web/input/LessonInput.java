package devluan.schoolapi.web.input;

import java.time.LocalDate;
import java.util.UUID;

public record LessonInput(
        UUID classroomId,
        UUID teacherId,
        LocalDate date
) {
}
