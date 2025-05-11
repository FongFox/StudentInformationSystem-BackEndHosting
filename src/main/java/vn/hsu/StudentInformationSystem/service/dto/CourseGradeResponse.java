package vn.hsu.StudentInformationSystem.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseGradeResponse {
    private String code;
    private String name;
    private int credit;
    private Double grade;

    public CourseGradeResponse() {
    }

    public CourseGradeResponse(String code, String name, int credit, Double grade) {
        this.code = code;
        this.name = name;
        this.credit = credit;
        this.grade = grade;
    }
}
