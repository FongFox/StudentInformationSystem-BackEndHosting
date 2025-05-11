package vn.hsu.StudentInformationSystem.service.mapper;

import org.springframework.stereotype.Component;
import vn.hsu.StudentInformationSystem.model.Course;
import vn.hsu.StudentInformationSystem.service.dto.CourseGradeResponse;

@Component
public class CourseMapper {
    public CourseGradeResponse toDto(Course course) {
        return new CourseGradeResponse(course.getCode(), course.getName(), course.getCredit(), course.getGrade());
    }
}
