package cegep.management.system.api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cegep.management.system.api.model.StudentEvaluation;
import cegep.management.system.api.model.StudentEvaluationId;

@Repository
public interface StudentEvaluationRepository extends JpaRepository<StudentEvaluation, StudentEvaluationId> {
    List<StudentEvaluation> findAllByIdStudentId(Long studentId);
}
