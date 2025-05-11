package vn.hsu.StudentInformationSystem.service.impl;

import org.springframework.stereotype.Service;
import vn.hsu.StudentInformationSystem.model.PhotocopyTransaction;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.repository.PhotocopyTransactionRepository;
import vn.hsu.StudentInformationSystem.service.PhotocopyTransactionService;

import java.util.List;

@Service
public class PhotocopyTransactionServiceImpl implements PhotocopyTransactionService {
    private final PhotocopyTransactionRepository photocopyTransactionRepository;

    public PhotocopyTransactionServiceImpl(PhotocopyTransactionRepository photocopyTransactionRepository) {
        this.photocopyTransactionRepository = photocopyTransactionRepository;
    }

    public List<PhotocopyTransaction> handleFetchAllByStudent(Student student) {
        return this.photocopyTransactionRepository.findAllByStudent(student);
    }
}
