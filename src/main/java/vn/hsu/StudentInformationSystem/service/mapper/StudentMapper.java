package vn.hsu.StudentInformationSystem.service.mapper;

import org.springframework.stereotype.Component;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.service.dto.StudentProfileResponse;

@Component
public class StudentMapper {
    public StudentProfileResponse toProfile(Student student) {
        StudentProfileResponse dto = new StudentProfileResponse();
        dto.setId(student.getId());
        dto.setCode(student.getCode());
        dto.setFullName(student.getFullName());
        dto.setUsername(student.getUsername());
        return dto;
    }
}
