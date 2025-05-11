package vn.hsu.StudentInformationSystem.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class CourseExamResponse {
    private String courseCode;
    private String courseName;
    private LocalDate examDate;
    private LocalTime examTime;

    public CourseExamResponse() {
    }

    public CourseExamResponse(String courseCode, String courseName, LocalDate examDate, LocalTime examTime) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.examDate = examDate;
        this.examTime = examTime;
    }
}
