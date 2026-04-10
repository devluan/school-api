package devluan.schoolapi.web.output;

import devluan.schoolapi.domain.classroom.Shift;

import java.util.UUID;

public record ClassroomOutput(
        UUID id,
        Integer grade,
        Character letter,
        Shift shift
) {
}