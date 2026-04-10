package devluan.schoolapi.web.handler;

import devluan.schoolapi.domain.attendance.exception.StudentAlreadyHasAttendanceException;
import devluan.schoolapi.domain.attendance.exception.StudentNotInThisClassroomException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorInfo> notFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo(404, e.getMessage()));
    }

    @ExceptionHandler(StudentNotInThisClassroomException.class)
    public ResponseEntity<ErrorInfo> studentNotInThisClassroom(StudentNotInThisClassroomException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(400, e.getMessage()));
    }

    @ExceptionHandler(StudentAlreadyHasAttendanceException.class)
    public ResponseEntity<ErrorInfo> studentAlreadyHasAttendance(StudentAlreadyHasAttendanceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(400, e.getMessage()));
    }
}