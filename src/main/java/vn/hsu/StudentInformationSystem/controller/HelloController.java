package vn.hsu.StudentInformationSystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("api/hello")
    public ResponseEntity<Object> sayHello() {
        return ResponseEntity.ok("Hello World From Spring Boot App!");
    }
}
