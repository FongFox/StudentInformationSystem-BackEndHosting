package vn.hsu.StudentInformationSystem.service;

import vn.hsu.StudentInformationSystem.model.Semester;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.model.Tuition;

import java.util.List;

public interface TuitionService {
    List<Tuition> handleFetchAllTuitionByStudent(Student student);

    List<Tuition> handleFetchAllTuitionByStudentAndSemester(Student student, Semester semester);
}
