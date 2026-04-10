package devluan.schoolapi.domain.attendance.exception;

public class StudentAlreadyHasAttendanceException extends RuntimeException {
    public StudentAlreadyHasAttendanceException(String message) {
        super(message);
    }
}
