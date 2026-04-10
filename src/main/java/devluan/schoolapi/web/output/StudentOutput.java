package devluan.schoolapi.web.output;

import java.time.LocalDate;
import java.util.UUID;

public record StudentOutput(
        UUID id,
        String name,
        LocalDate birthDate,
        UUID classroomId
) {
}