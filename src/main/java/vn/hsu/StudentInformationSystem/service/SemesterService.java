package vn.hsu.StudentInformationSystem.service;

import vn.hsu.StudentInformationSystem.model.Semester;
import vn.hsu.StudentInformationSystem.model.Student;

import java.util.List;

public interface SemesterService {
    void handleCreateSemester(Semester semester);

    Semester handleFetchSemesterByCode(long code);

    List<Semester> handleFetchSemesterByStudent(Student student);

    long handleCheckSemesterQuantity();
}
