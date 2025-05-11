package vn.hsu.StudentInformationSystem.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.service.StudentService;
import vn.hsu.StudentInformationSystem.service.dto.LoginRequest;
import vn.hsu.StudentInformationSystem.service.dto.LoginResponse;
import vn.hsu.StudentInformationSystem.service.dto.StudentProfileResponse;
import vn.hsu.StudentInformationSystem.service.mapper.StudentMapper;
import vn.hsu.StudentInformationSystem.util.SecurityUtils;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtils securityUtils;
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtils securityUtils, StudentService studentService, StudentMapper studentMapper) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtils = securityUtils;
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        //Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()
        );

        //xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //set thông tin người dùng đăng nhập vào context (có thể tái sử dụng)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //create new login response
        LoginResponse loginResponse = new LoginResponse();
        //add response student
        Student dbStudent = this.studentService.handleFetchStudentByUsername(loginRequest.getUsername());
        StudentProfileResponse studentProfileResponse = this.studentMapper.toProfile(dbStudent);
        loginResponse.setStudentProfileResponse(studentProfileResponse);

        //create access token
        String accessToken = this.securityUtils.createAccessToken(authentication, loginResponse);
        //add access token
        loginResponse.setAccessToken(accessToken);

        //create new refresh token
        String refreshToken = this.securityUtils.createRefreshToken(loginRequest.getUsername(), loginResponse);
        //update student token
        this.studentService.handleUpdateStudentToken(refreshToken, loginRequest.getUsername());

        //set response cookie
        ResponseCookie responseCookie = ResponseCookie
                .from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(this.securityUtils.getRefreshTokenExpiration())
                .build();

        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(loginResponse);
    }

    @GetMapping("refresh")
    public ResponseEntity<LoginResponse> fetchRefreshToken(@CookieValue(name = "refreshToken") String refreshToken) {
        //check is data valid
        Jwt decodedToken = this.securityUtils.checkValidRefreshToken(refreshToken);
        String username = decodedToken.getSubject();

        //check user by user + token
        Student student = this.studentService.handleFetchStudentByUsernameAndRefreshToken(username, refreshToken);

        //issue new token / set refresh token as cookies

        //create new login response
        LoginResponse loginResponse = new LoginResponse();
        //add response student
        Student dbStudent = this.studentService.handleFetchStudentByUsername(username);
        StudentProfileResponse studentProfileResponse = this.studentMapper.toProfile(dbStudent);
        loginResponse.setStudentProfileResponse(studentProfileResponse);

        //create access token
        String accessToken = this.securityUtils.createAccessToken(username, loginResponse);
        //add access token
        loginResponse.setAccessToken(accessToken);

        //create new refresh token
        String newRefreshToken = this.securityUtils.createRefreshToken(username, loginResponse);
        //update student token
        this.studentService.handleUpdateStudentToken(newRefreshToken, username);

        //set response cookie
        ResponseCookie responseCookie = ResponseCookie
                .from("refreshToken", newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(this.securityUtils.getRefreshTokenExpiration())
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(loginResponse);
    }

    @PostMapping("logout")
    public ResponseEntity<Void> logout() {
        //lấy username từ token
        String username = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new EntityNotFoundException("Student with username not found"));

        //update refresh token = null
        this.studentService.handleUpdateStudentToken(null, username);

        //remove response cookie
        ResponseCookie deleteSpringCookie = ResponseCookie
                .from("refreshToken", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString())
                .body(null);
    }
}
