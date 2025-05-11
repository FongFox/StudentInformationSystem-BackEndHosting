package vn.hsu.StudentInformationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hsu.StudentInformationSystem.model.Course;
import vn.hsu.StudentInformationSystem.model.Semester;
import vn.hsu.StudentInformationSystem.model.Student;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    /**
     * Lấy tất cả các khóa của 1 sinh viên trong 1 học kỳ
     * Đầu vào là student & course id
     */
    List<Course> findAllByStudentIdAndSemesterId(Long studentId, Long semesterId);

    /**
     * Lấy tất cả các khóa của 1 sinh viên trong 1 học kỳ
     * Đầu vào là student & course
     */
    List<Course> findAllByStudentAndSemester(Student student, Semester semester);
}
