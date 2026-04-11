package devluan.schoolapi.web.mapping;

import devluan.schoolapi.domain.attendance.Attendance;
import devluan.schoolapi.domain.lesson.Lesson;
import devluan.schoolapi.domain.lesson.LessonService;
import devluan.schoolapi.domain.student.Student;
import devluan.schoolapi.domain.student.StudentService;
import devluan.schoolapi.web.AttendanceAPI;
import devluan.schoolapi.web.input.AttendanceInput;
import devluan.schoolapi.web.output.AttendanceOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class AttendanceMapper {
    private final StudentService studentService;
    private final LessonService lessonService;

    public Page<EntityModel<AttendanceOutput>> map(Page<Attendance> page) {
        return page.map(this::buildOutputModel);
    }

    public Attendance map(AttendanceInput input) {
        Attendance attendance = new Attendance();
        Lesson lesson = lessonService.findLesson(input.lessonId());
        Student student = studentService.findStudent(input.studentId());
        attendance.setLesson(lesson);
        attendance.setStudent(student);
        attendance.setStatus(input.status());
        return attendance;
    }

    public AttendanceOutput map(Attendance attendance) {
        return new AttendanceOutput(
                attendance.getId(),
                attendance.getLesson().getId(),
                attendance.getStudent().getId(),
                attendance.getStatus()
        );
    }

    public EntityModel<AttendanceOutput> buildOutputModel(Attendance attendance) {
        AttendanceOutput output = this.map(attendance);
        EntityModel<AttendanceOutput> model = EntityModel.of(output);
        model
                .add(linkTo(methodOn(AttendanceAPI.class)
                        .findAttendance(attendance.getId()))
                        .withSelfRel())
                .add(linkTo(methodOn(AttendanceAPI.class)
                        .listAttendances(0,10))
                        .withRel("Attendances")
                        .withType("GET"));
        return model;
    }
}
