package vn.hsu.StudentInformationSystem.service.mapper;

import org.springframework.stereotype.Component;
import vn.hsu.StudentInformationSystem.model.Course;
import vn.hsu.StudentInformationSystem.service.dto.CourseExamResponse;

@Component
public class CourseExamMapper {
    public CourseExamResponse toDto(Course course) {
        return new CourseExamResponse(
                course.getCode(),
                course.getName(),
                course.getFinalExamDate(),
                course.getFinalExamTime()
        );
    }
}
