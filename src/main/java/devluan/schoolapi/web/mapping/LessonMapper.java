package devluan.schoolapi.web.mapping;

import devluan.schoolapi.domain.classroom.Classroom;
import devluan.schoolapi.domain.classroom.ClassroomService;
import devluan.schoolapi.domain.lesson.Lesson;
import devluan.schoolapi.domain.teacher.Teacher;
import devluan.schoolapi.domain.teacher.TeacherService;
import devluan.schoolapi.web.LessonAPI;
import devluan.schoolapi.web.input.LessonInput;
import devluan.schoolapi.web.output.LessonOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class LessonMapper {
    private final ClassroomService classroomService;
    private final TeacherService teacherService;

    public Page<EntityModel<LessonOutput>> map(Page<Lesson> page) {
        return page.map(this::buildOutputModel);
    }

    public Lesson map(LessonInput input) {
        Lesson lesson = new Lesson();
        Classroom classroom = classroomService.findClassroom(input.classroomId());
        Teacher teacher = teacherService.findTeacher(input.teacherId());
        lesson.setClassroom(classroom);
        lesson.setTeacher(teacher);
        lesson.setDate(input.date());
        return lesson;
    }

    public LessonOutput map(Lesson lesson) {
        return new LessonOutput(
                lesson.getId(),
                lesson.getClassroom().getId(),
                lesson.getTeacher().getId(),
                lesson.getDate()
        );
    }

    public EntityModel<LessonOutput> buildOutputModel(Lesson lesson) {
        LessonOutput output = this.map(lesson);
        EntityModel<LessonOutput> model = EntityModel.of(output);
        model
                .add(linkTo(methodOn(LessonAPI.class)
                        .findLesson(lesson.getId()))
                        .withSelfRel())
                .add(linkTo(methodOn(LessonAPI.class)
                        .findAttendanceByStudents(lesson.getId(), 0, 10))
                        .withRel("attendances")
                        .withType("GET"))
                .add((linkTo(methodOn(LessonAPI.class)
                        .listLessons(0, 10))
                        .withRel("lesson list"))
                        .withType("GET"));
        return model;
    }
}