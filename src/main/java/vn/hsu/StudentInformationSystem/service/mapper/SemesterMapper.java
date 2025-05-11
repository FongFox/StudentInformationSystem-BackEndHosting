package vn.hsu.StudentInformationSystem.service.mapper;

import org.springframework.stereotype.Component;
import vn.hsu.StudentInformationSystem.model.Semester;
import vn.hsu.StudentInformationSystem.service.dto.SemesterResponse;

@Component
public class SemesterMapper {
    public SemesterResponse toDto(Semester semester) {
        return new SemesterResponse(semester.getCode(), semester.getYear(), semester.getShortDescription());
    }
}
