package vn.hsu.StudentInformationSystem.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String accessToken;
    private StudentProfileResponse studentProfileResponse;

    public LoginResponse() {
        this.studentProfileResponse = new StudentProfileResponse();
    }
}
