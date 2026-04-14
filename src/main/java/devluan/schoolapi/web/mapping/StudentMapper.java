package devluan.schoolapi.web.mapping;

import devluan.schoolapi.domain.classroom.Classroom;
import devluan.schoolapi.domain.classroom.ClassroomService;
import devluan.schoolapi.domain.student.Student;
import devluan.schoolapi.domain.student.StudentAttendanceRate;
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

    public Page<EntityModel<StudentOutput>> mapAttendanceRates(Page<StudentAttendanceRate> page) {
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

    public StudentOutput map(StudentAttendanceRate student) {
        return new StudentOutput(
                student.getId(),
                student.getName(),
                student.getBirthDate(),
                student.getClassroomId()
        );
    }

    public EntityModel<StudentOutput> buildOutputModel(Student student) {
        return this.buildOutputModel(this.map(student));
    }

    public EntityModel<StudentOutput> buildOutputModel(StudentAttendanceRate student) {
        return this.buildOutputModel(this.map(student));
    }

    public EntityModel<StudentOutput> buildOutputModel(StudentOutput output) {
        EntityModel<StudentOutput> model = EntityModel.of(output);
        model
                .add(linkTo(methodOn(StudentAPI.class)
                        .findStudent(output.id()))
                        .withSelfRel())
                .add(linkTo(methodOn(StudentAPI.class)
                        .listStudents(0, 10, 0, 100))
                        .withRel("students")
                        .withType("GET"))
                .add(linkTo(methodOn(StudentAPI.class)
                        .listAttendancesByStudents(output.id(), 0, 10))
                        .withRel("attendances")
                        .withType("GET"));;
        if (output.classroomId() != null) {
            model.add(linkTo(methodOn(ClassroomAPI.class)
                    .findClassroom(output.classroomId()))
                    .withRel("classroom")
                    .withType("GET"));
        }
        return model;
    }
}