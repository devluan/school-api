package devluan.schoolapi.web.mapping;

import devluan.schoolapi.domain.classroom.Classroom;
import devluan.schoolapi.web.ClassroomAPI;
import devluan.schoolapi.web.input.ClassroomInput;
import devluan.schoolapi.web.output.ClassroomOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClassroomMapper {

    public Page<EntityModel<ClassroomOutput>> map(Page<Classroom> page) {
        return page.map(this::buildOutputModel);
    }

    public Classroom map(ClassroomInput input) {
        Classroom classroom = new Classroom();
        BeanUtils.copyProperties(input, classroom);
        return classroom;
    }

    public ClassroomOutput map(Classroom classroom) {
        return new ClassroomOutput(
                classroom.getId(),
                classroom.getGrade(),
                classroom.getLetter(),
                classroom.getShift()
        );
    }

    public EntityModel<ClassroomOutput> buildOutputModel(Classroom classroom) {
        ClassroomOutput output = this.map(classroom);
        EntityModel<ClassroomOutput> model = EntityModel.of(output);
        model
                .add(linkTo(methodOn(ClassroomAPI.class)
                        .findClassroom(classroom.getId()))
                        .withSelfRel())
                .add(linkTo(methodOn(ClassroomAPI.class)
                        .listClassrooms(0, 10))
                        .withRel("classrooms")
                        .withType("GET"))
                .add(linkTo(methodOn(ClassroomAPI.class)
                        .findStudentsByClassroom(classroom.getId(), 0, 10))
                        .withRel("students")
                        .withType("GET"));
        return model;
    }
}