package vn.hsu.StudentInformationSystem.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeQueryRequest {
    private long semesterCode;

    public GradeQueryRequest() {
    }
}
