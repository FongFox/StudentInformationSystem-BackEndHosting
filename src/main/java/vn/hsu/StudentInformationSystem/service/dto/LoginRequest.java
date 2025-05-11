package vn.hsu.StudentInformationSystem.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "username can't be blank!")
    private String username;

    @NotBlank(message = "password can't be blank!")
    private String password;

    public LoginRequest() {
    }
}
