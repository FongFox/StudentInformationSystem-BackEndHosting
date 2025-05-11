package vn.hsu.StudentInformationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "courses")
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String code;

    private String name;

    private int credit;

    @Column(nullable = true)
    private long price;

    @Column(nullable = true)
    private double grade;

    @Column(name = "final_exam_date")
    private LocalDate finalExamDate;

    @Column(name = "final_exam_time")
    private LocalTime finalExamTime;

    @ManyToOne()
    @JoinColumn(
            name = "semester_id",
            foreignKey = @ForeignKey(name = "fk_course_semester")
    )
    private Semester semester;

    @ManyToOne
    @JoinColumn(
            name = "student_id",
            foreignKey = @ForeignKey(name = "fk_course_student")
    )
    private Student student;

    public Course() {
    }

    public Course(String code, String name, int credit, long price) {
        this.code = code;
        this.name = name;
        this.credit = credit;
        this.price = price;
    }
}
