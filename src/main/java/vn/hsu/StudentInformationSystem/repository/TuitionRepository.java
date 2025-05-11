package vn.hsu.StudentInformationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hsu.StudentInformationSystem.model.Semester;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.model.Tuition;

import java.util.List;

@Repository
public interface TuitionRepository extends JpaRepository<Tuition, Long> {
    List<Tuition> findAllByStudent(Student student);

    List<Tuition> findAllByStudentAndSemester(Student student, Semester semester);
}
