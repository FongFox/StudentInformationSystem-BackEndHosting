package vn.hsu.StudentInformationSystem.service.impl;

import org.springframework.stereotype.Service;
import vn.hsu.StudentInformationSystem.model.Semester;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.model.Tuition;
import vn.hsu.StudentInformationSystem.repository.TuitionRepository;
import vn.hsu.StudentInformationSystem.service.TuitionService;

import java.util.List;

@Service
public class TuitionServiceImpl implements TuitionService {
    private final TuitionRepository tuitionRepository;

    public TuitionServiceImpl(TuitionRepository tuitionRepository) {
        this.tuitionRepository = tuitionRepository;
    }

    @Override
    public List<Tuition> handleFetchAllTuitionByStudent(Student student) {
        return this.tuitionRepository.findAllByStudent(student);
    }

    @Override
    public List<Tuition> handleFetchAllTuitionByStudentAndSemester(Student student, Semester semester) {
        return this.tuitionRepository.findAllByStudentAndSemester(student, semester);
    }
}
