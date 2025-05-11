package vn.hsu.StudentInformationSystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tuition")
public class Tuition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = true)
    private long total;

    @Column(nullable = true)
    private long paid;

    @Column(nullable = true)
    private long refund;

    @Column(nullable = true)
    private long balance;

    @Column(name = "is_paid")
    private boolean isPaid;

    @ManyToOne()
    @JoinColumn(
            name = "semester_id",
            foreignKey = @ForeignKey(name = "fk_tuition_semester")
    )
    private Semester semester;

    @ManyToOne()
    @JoinColumn(
            name = "student_id",
            foreignKey = @ForeignKey(name = "fk_tuition_student")
    )
    private Student student;

    public Tuition() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPaid() {
        return paid;
    }

    public long getRefund() {
        return refund;
    }

    public void setRefund(long refund) {
        this.refund = refund;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(long paid) {
        this.paid = paid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
