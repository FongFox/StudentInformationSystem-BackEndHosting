package vn.hsu.StudentInformationSystem.service;

import vn.hsu.StudentInformationSystem.model.PhotocopyTransaction;
import vn.hsu.StudentInformationSystem.model.Student;

import java.util.List;

public interface PhotocopyTransactionService {
    List<PhotocopyTransaction> handleFetchAllByStudent(Student student);
}
