package devluan.schoolapi.web.mapping;

import devluan.schoolapi.domain.subject.Subject;
import devluan.schoolapi.web.SubjectAPI;
import devluan.schoolapi.web.input.SubjectInput;
import devluan.schoolapi.web.output.SubjectOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SubjectMapper {

    public Page<EntityModel<SubjectOutput>> map(Page<Subject> page) {
        return page.map(this::buildOutputModel);
    }

    public Subject map(SubjectInput input) {
        Subject subject = new Subject();
        BeanUtils.copyProperties(input, subject);
        return subject;
    }

    public SubjectOutput map(Subject subject) {
        return new SubjectOutput(
                subject.getId(),
                subject.getName()
        );
    }

    public EntityModel<SubjectOutput> buildOutputModel(Subject subject) {
        SubjectOutput output = this.map(subject);
        EntityModel<SubjectOutput> model = EntityModel.of(output);
        model
                .add(linkTo(methodOn(SubjectAPI.class)
                        .findSubject(subject.getId()))
                        .withSelfRel())
                .add(linkTo(methodOn(SubjectAPI.class)
                        .listSubjects(0, 10))
                        .withRel("subjects list")
                        .withType("GET"))
                .add(linkTo(methodOn(SubjectAPI.class)
                        .findTeachersBySubject(subject.getId(), 0, 10))
                        .withRel("teachers")
                        .withType("GET"));
        return model;
    }
}