package devluan.schoolapi.domain.subject;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public Page<Subject> listSubjects(Pageable pageable) {
        return subjectRepository.findAllByRemovedFalse(pageable);
    }

    public Subject findSubject(UUID id) {
        return subjectRepository
                .findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found"));
    }

    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Subject updateSubject(UUID id, Subject updated) {
        Subject subject = findSubject(id);
        subject.setName(updated.getName());
        return subject;
    }

    public void deleteSubject(UUID id) {
        Subject subject = subjectRepository.getReferenceById(id);
        subject.setRemoved(true);
        subjectRepository.save(subject);
    }
}