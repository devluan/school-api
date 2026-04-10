package devluan.schoolapi.web.input;

import devluan.schoolapi.domain.classroom.Shift;

public record ClassroomInput(
        Integer grade,
        Character letter,
        Shift shift
) {
}