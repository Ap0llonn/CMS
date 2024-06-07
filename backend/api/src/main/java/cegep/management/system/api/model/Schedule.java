package cegep.management.system.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

    private String hourStart;
    private String hourEnd;

    public Schedule(Student student, Course course, String hourStart, String hourEnd) {
        this.student = student;
        this.course = course;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
    }
}
