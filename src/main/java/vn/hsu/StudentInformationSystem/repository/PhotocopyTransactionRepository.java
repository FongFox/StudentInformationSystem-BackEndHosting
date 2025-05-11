package vn.hsu.StudentInformationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hsu.StudentInformationSystem.model.PhotocopyTransaction;
import vn.hsu.StudentInformationSystem.model.Student;

import java.util.List;

@Repository
public interface PhotocopyTransactionRepository extends JpaRepository<PhotocopyTransaction, Long> {
    List<PhotocopyTransaction> findAllByStudent(Student student);
}
