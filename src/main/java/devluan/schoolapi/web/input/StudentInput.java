package devluan.schoolapi.web.input;

import java.time.LocalDate;
import java.util.UUID;

public record StudentInput(
        String name,
        LocalDate birthDate,
        UUID classroomId
) {
}