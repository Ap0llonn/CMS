package cegep.management.system.api.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEvaluation {

    @EmbeddedId
    private StudentEvaluationId id;

    @ManyToOne
    @MapsId("studentId")
    private Student student;

    @ManyToOne
    @MapsId("evaluationId")
    private Evaluation evaluation;

    public StudentEvaluation(Long studentId, Long courseId) {
        this.id = new StudentEvaluationId(studentId, courseId);
    }
}
