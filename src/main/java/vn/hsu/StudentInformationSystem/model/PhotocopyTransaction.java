package vn.hsu.StudentInformationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "photocopy_transactions")
@Setter
@Getter
public class PhotocopyTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;

    @Column(nullable = true)
    private long amount;

    @ManyToOne()
    @JoinColumn(
            name = "student_id",
            foreignKey = @ForeignKey(name = "fk_photocopy_transaction_student")
    )
    private Student student;

    public PhotocopyTransaction() {
    }
}
