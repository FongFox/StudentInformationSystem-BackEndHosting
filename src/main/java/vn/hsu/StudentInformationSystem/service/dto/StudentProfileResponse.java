package vn.hsu.StudentInformationSystem.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentProfileResponse {
    private long id;
    private long code;
    private String fullName;
    private String username;

    public StudentProfileResponse() {
    }
}
