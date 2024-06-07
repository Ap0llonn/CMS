package cegep.management.system.api.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @ManyToOne
    @JoinColumn(name = "year_id")
    private AcademicYear academicYear;

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    public Session(AcademicYear academicYear, String name, LocalDate startDate, LocalDate endDate) {
        this.academicYear = academicYear;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
