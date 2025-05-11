package vn.hsu.StudentInformationSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Setter
@Getter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = true)
    private long code;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "photocopy_balance", nullable = true)
    private long photocopyBalance;

    @Column(name = "user_name")
    private String username;

    private String password;

    @Column(name = "refresh_token", columnDefinition = "TEXT")
    private String refreshToken;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Course> courseList;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PhotocopyTransaction> photocopyTransactionList;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Tuition> tuitionList;

    public Student() {
        this.courseList = new ArrayList<>();
        this.photocopyTransactionList = new ArrayList<>();
        this.tuitionList = new ArrayList<>();
    }

    public Student(long code, String fullName, String username, String password) {
        this.code = code;
        this.fullName = fullName;
        this.username = username;
        this.password = password;

        this.courseList = new ArrayList<>();
        this.photocopyTransactionList = new ArrayList<>();
        this.tuitionList = new ArrayList<>();
    }
}
