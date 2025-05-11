package vn.hsu.StudentInformationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hsu.StudentInformationSystem.model.Semester;
import vn.hsu.StudentInformationSystem.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
    Optional<Semester> findSemesterByCode(long code);

    List<Semester> findDistinctByCourseListStudent(Student student);
}
