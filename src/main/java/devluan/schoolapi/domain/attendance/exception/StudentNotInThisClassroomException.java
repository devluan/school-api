package devluan.schoolapi.domain.attendance.exception;

public class StudentNotInThisClassroomException extends RuntimeException {
    public StudentNotInThisClassroomException(String message) {
        super(message);
    }
}
