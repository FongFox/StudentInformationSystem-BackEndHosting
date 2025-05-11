package vn.hsu.StudentInformationSystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.repository.StudentRepository;
import vn.hsu.StudentInformationSystem.service.StudentService;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void handleCreateStudent(Student student) {
        //Hash password for new user
        String hashPassword = this.passwordEncoder.encode(student.getPassword());
        student.setPassword(hashPassword);

        //Save again with final username
        this.studentRepository.save(student);
    }

    @Override
    public Student handleFetchStudentById(long id) {
        Optional<Student> studentOptional = this.studentRepository.findById(id);

        return studentOptional.orElseThrow(
                () -> new EntityNotFoundException("Student with ID " + id + " not found")
        );
    }

    @Override
    public Student handleFetchStudentByUsername(String username) {
        // TODO Auto-generated method stub
        Optional<Student> studentOptional = this.studentRepository.findByUsername(username);

        return studentOptional.orElseThrow(
                () -> new EntityNotFoundException("Student with username not found")
        );
    }

    @Override
    public Optional<Student> handleFetchStudentOptionalByUsername(String username) {
        return this.studentRepository.findByUsername(username);
    }

    @Override
    public Student handleFetchStudentByUsernameAndRefreshToken(String username, String token) {
        Optional<Student> studentOptional = this.studentRepository.findByUsernameAndRefreshToken(username, token);

        return studentOptional.orElseThrow(
                () -> new EntityNotFoundException("Student with username or refresh token not found")
        );
    }

    @Override
    public List<Student> handleFetchStudentList() {
        return this.studentRepository.findAll();
    }

    @Override
    public void handleUpdateStudentPassword(long id, String password) {
        Student dbStudent = this.handleFetchStudentById(id);

        String hashPassword = this.passwordEncoder.encode(password);
        dbStudent.setPassword(hashPassword);

        this.studentRepository.save(dbStudent);
    }

    @Override
    public long handleCheckUserQuantity() {
        return this.studentRepository.count();
    }

    @Override
    public void handleUpdateStudentToken(String token, String username) {
        Student currentStudent = this.handleFetchStudentByUsername(username);
        currentStudent.setRefreshToken(token);

        this.studentRepository.save(currentStudent);
    }

}
