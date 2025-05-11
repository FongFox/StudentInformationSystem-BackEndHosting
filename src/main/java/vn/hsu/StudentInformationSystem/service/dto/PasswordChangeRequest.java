package vn.hsu.StudentInformationSystem.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeRequest {
    private String newPassword;

    public PasswordChangeRequest() {
    }
}
