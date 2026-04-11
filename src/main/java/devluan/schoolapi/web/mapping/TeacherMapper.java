package devluan.schoolapi.web.mapping;

import devluan.schoolapi.domain.subject.Subject;
import devluan.schoolapi.domain.subject.SubjectService;
import devluan.schoolapi.domain.teacher.Teacher;
import devluan.schoolapi.web.SubjectAPI;
import devluan.schoolapi.web.TeacherAPI;
import devluan.schoolapi.web.input.TeacherInput;
import devluan.schoolapi.web.output.TeacherOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class TeacherMapper {
    private final SubjectService subjectService;

    public Page<EntityModel<TeacherOutput>> map(Page<Teacher> page) {
        return page.map(this::buildOutputModel);
    }

    public Teacher map(TeacherInput input) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(input, teacher);
        Subject subject = input.subjectId() != null ? subjectService.findSubject(input.subjectId()) : null;
        teacher.setSubject(subject);
        return teacher;
    }

    public TeacherOutput map(Teacher teacher) {
        return new TeacherOutput(
                teacher.getId(),
                teacher.getName(),
                teacher.getSubject() != null ? teacher.getSubject().getId() : null
        );
    }

    public EntityModel<TeacherOutput> buildOutputModel(Teacher teacher) {
        TeacherOutput output = this.map(teacher);
        EntityModel<TeacherOutput> model = EntityModel.of(output);
        model
                .add(linkTo(methodOn(TeacherAPI.class)
                        .findTeacher(teacher.getId()))
                        .withSelfRel())
                .add(linkTo(methodOn(TeacherAPI.class)
                        .listTeachers(0, 10))
                        .withRel("teachers")
                        .withType("GET"));
        if (teacher.getSubject() != null) {
            model
                    .add(linkTo(methodOn(SubjectAPI.class)
                    .findSubject(teacher.getSubject().getId()))
                            .withRel("subject")
                            .withType("GET"));
        }
        return model;
    }
}