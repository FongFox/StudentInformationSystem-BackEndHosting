package vn.hsu.StudentInformationSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "semester")
@Getter
@Setter
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = true)
    private long code;

    @Column(nullable = true)
    private int year;

    @Column(name = "short_description")
    private String shortDescription;

    @OneToMany(mappedBy = "semester", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Course> courseList;

    @OneToMany(mappedBy = "semester", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Tuition> tuitionList;

    public Semester() {
        this.courseList = new ArrayList<>();
        this.tuitionList = new ArrayList<>();
    }

    public Semester(long code, int year, String shortDescription) {
        this.code = code;
        this.year = year;
        this.shortDescription = shortDescription;

        this.courseList = new ArrayList<>();
        this.tuitionList = new ArrayList<>();
    }
}
