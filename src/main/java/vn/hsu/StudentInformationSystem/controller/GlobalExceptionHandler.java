package vn.hsu.StudentInformationSystem.controller;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import vn.hsu.StudentInformationSystem.service.dto.RestResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RestResponse<Object>> handleNotFound(EntityNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        RestResponse<Object> resp = new RestResponse<>();
        resp.setStatus(HttpStatus.NOT_FOUND.value());
        resp.setMessage("Resource not found");
        resp.setError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<RestResponse<Object>> handleUnauthorized(Exception ex) {
        log.warn("Unauthorized access: {}", ex.getMessage());
        RestResponse<Object> resp = new RestResponse<>();
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.setMessage("Authentication failed");
        resp.setError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.warn("Bad request type mismatch: {}", ex.getMessage());
        RestResponse<Object> resp = new RestResponse<>();
        resp.setStatus(HttpStatus.BAD_REQUEST.value());
        resp.setMessage("Invalid parameter type");
        resp.setError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.warn("Validation errors: {}", ex.getMessage());
        BindingResult result = ex.getBindingResult();
        List<String> errors = result.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        RestResponse<Object> resp = new RestResponse<>();
        resp.setStatus(HttpStatus.BAD_REQUEST.value());
        resp.setMessage("Validation failed");
        resp.setError(errors.size() == 1 ? errors.get(0) : errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

    @ExceptionHandler(ScriptException.class)
    public ResponseEntity<RestResponse<Object>> handleScriptError(ScriptException ex) {
        log.error("Database initialization error: {}", ex.getMessage(), ex);
        RestResponse<Object> resp = new RestResponse<>();
        resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        resp.setMessage("Database initialization failed");
        resp.setError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<RestResponse<Object>> handleMissingCookie(MissingRequestCookieException ex) {
        log.warn("Missing cookie: {}", ex.getMessage());
        RestResponse<Object> resp = new RestResponse<>();
        resp.setStatus(HttpStatus.BAD_REQUEST.value());
        resp.setMessage("Required cookie is missing");
        resp.setError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<Object>> handleGeneric(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        RestResponse<Object> resp = new RestResponse<>();
        resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        resp.setMessage("An unexpected error occurred");
        resp.setError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
    }

//    @ExceptionHandler(value = {
//            MethodArgumentTypeMismatchException.class,
//            EntityNotFoundException.class,
//            UsernameNotFoundException.class,
//            BadCredentialsException.class,
//            JwtException.class,
//            MissingRequestCookieException.class
//    })
//    public ResponseEntity<RestResponse<Object>> handleException(Exception exception) {
//        HttpStatus status;
//
//        if (exception instanceof EntityNotFoundException || exception instanceof UsernameNotFoundException) {
//            status = HttpStatus.NOT_FOUND;
//        } else if (exception instanceof BadCredentialsException || exception instanceof JwtException) {
//            status = HttpStatus.UNAUTHORIZED;
//        } else if (exception instanceof MethodArgumentTypeMismatchException) {
//            status = HttpStatus.BAD_REQUEST;
//        } else {
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        RestResponse<Object> restResponse = new RestResponse<Object>();
//        restResponse.setStatus(status.value());
//        restResponse.setMessage("Call API Failed!");
//        restResponse.setError(exception.getMessage());
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<RestResponse<Object>> handleValidationError(MethodArgumentNotValidException ex) {
//        BindingResult result = ex.getBindingResult();
//        final List<FieldError> fieldErrors = result.getFieldErrors();
//
//        RestResponse<Object> restResponse = new RestResponse<Object>();
//        restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
//        restResponse.setMessage(ex.getBody().getDetail());
//
//        List<String> errors = fieldErrors.stream().map(f -> f.getDefaultMessage()).collect(Collectors.toList());
//        List<String> errors = new ArrayList<>();
//        for (FieldError fieldError : fieldErrors) {
//            errors.add(fieldError.getDefaultMessage());
//        }
//        restResponse.setError(errors.size() > 1 ? errors : errors.get(0));
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
//    }
}
