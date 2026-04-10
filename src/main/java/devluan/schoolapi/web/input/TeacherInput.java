package devluan.schoolapi.web.input;

import java.util.UUID;

public record TeacherInput(
        String name,
        UUID subjectId
) {
}