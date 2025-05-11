package vn.hsu.StudentInformationSystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import vn.hsu.StudentInformationSystem.model.Semester;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.repository.SemesterRepository;
import vn.hsu.StudentInformationSystem.service.SemesterService;

import java.util.List;
import java.util.Optional;

@Service
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepository;

    public SemesterServiceImpl(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    @Override
    public void handleCreateSemester(Semester semester) {
        this.semesterRepository.save(semester);
    }

    @Override
    public Semester handleFetchSemesterByCode(long code) {
        Optional<Semester> semesterOptional = this.semesterRepository.findSemesterByCode(code);
        return semesterOptional.orElseThrow(
                () -> new EntityNotFoundException("Semester with code " + code + " not found!")
        );
    }

    @Override
    public List<Semester> handleFetchSemesterByStudent(Student student) {
        return this.semesterRepository.findDistinctByCourseListStudent(student);
    }

    @Override
    public long handleCheckSemesterQuantity() {
        return this.semesterRepository.count();
    }
}
