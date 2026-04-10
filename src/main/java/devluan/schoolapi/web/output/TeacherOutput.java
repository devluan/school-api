package devluan.schoolapi.web.output;

import java.util.UUID;

public record TeacherOutput(
        UUID id,
        String name,
        UUID subjectId
) {
}