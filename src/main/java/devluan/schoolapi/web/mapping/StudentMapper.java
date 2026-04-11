package devluan.schoolapi.web.mapping;

import devluan.schoolapi.domain.classroom.Classroom;
import devluan.schoolapi.domain.classroom.ClassroomService;
import devluan.schoolapi.domain.student.Student;
import devluan.schoolapi.web.ClassroomAPI;
import devluan.schoolapi.web.StudentAPI;
import devluan.schoolapi.web.input.StudentInput;
import devluan.schoolapi.web.output.StudentOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class StudentMapper {
    private final ClassroomService classroomService;

    public Page<EntityModel<StudentOutput>> map(Page<Student> page) {
        return page.map(this::buildOutputModel);
    }

    public Student map(StudentInput input) {
        Student student = new Student();
        BeanUtils.copyProperties(input, student);
        Classroom classroom = input.classroomId() != null ? classroomService.findClassroom(input.classroomId()) : null;
        student.setClassroom(classroom);
        return student;
    }

    public StudentOutput map(Student student) {
        return new StudentOutput(
                student.getId(),
                student.getName(),
                student.getBirthDate(),
                student.getClassroom() != null ? student.getClassroom().getId() : null
        );
    }

    public EntityModel<StudentOutput> buildOutputModel(Student student) {
        StudentOutput output = this.map(student);
        EntityModel<StudentOutput> model = EntityModel.of(output);
        model
                .add(linkTo(methodOn(StudentAPI.class)
                        .findStudent(student.getId()))
                        .withSelfRel())
                .add(linkTo(methodOn(StudentAPI.class)
                        .listStudents(0, 10))
                        .withRel("students")
                        .withType("GET"))
                .add(linkTo(methodOn(StudentAPI.class)
                        .listAttendancesByStudents(student.getId(), 0, 10))
                        .withRel("attendances")
                        .withType("GET"));;
        if (student.getClassroom() != null) {
            model.add(linkTo(methodOn(ClassroomAPI.class)
                    .findClassroom(student.getClassroom().getId()))
                    .withRel("classroom")
                    .withType("GET"));
        }
        return model;
    }
}