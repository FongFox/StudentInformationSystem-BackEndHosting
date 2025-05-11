package vn.hsu.StudentInformationSystem.service;

import vn.hsu.StudentInformationSystem.model.Course;

import java.util.List;

public interface CourseService {
    void handleCreateCourse(Course course);

    Course handleFetchCourseById(long id);

    long handleCheckCourseQuantity();

    /**
     * Lấy khóa + điểm theo studentId và semesterCode
     */
    List<Course> handleFetchCoursesByStudentAndSemesterCode(Long studentId, Long semesterCode);

    /**
     * Lấy khóa + ngày, giờ kiểm tra theo studentId và semesterCode
     */
    List<Course> handleFetchExamScheduleByStudentAndSemesterCode(Long studentId, long semesterCode);
}
