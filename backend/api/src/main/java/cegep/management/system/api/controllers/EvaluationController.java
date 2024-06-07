package cegep.management.system.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cegep.management.system.api.dto.EvaluationDTO;
import cegep.management.system.api.model.Evaluation;
import cegep.management.system.api.model.Student;
import cegep.management.system.api.service.EvaluationService;

import java.util.List;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping
    public ResponseEntity<List<Evaluation>> getAllEvaluations() {
        List<Evaluation> evaluations = evaluationService.getAllEvaluations();
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/{evaluationId}")
    public ResponseEntity<Evaluation> getEvaluationById(@PathVariable Long evaluationId) {
        return evaluationService.getEvaluationById(evaluationId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Evaluation> createEvaluation(@RequestBody EvaluationDTO evaluationDTO) {
        Evaluation createdEvaluation = evaluationService.createEvaluation(evaluationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvaluation);
    }

    @PutMapping("/{evaluationId}")
    public ResponseEntity<Evaluation> updateEvaluation(@PathVariable Long evaluationId,
            @RequestBody Evaluation updatedEvaluation) {
        return evaluationService.getEvaluationById(evaluationId)
                .map(evaluation -> {
                    Evaluation updatedEvaluationEntity = evaluationService.updateEvaluation(evaluationId,
                            updatedEvaluation);
                    return ResponseEntity.ok(updatedEvaluationEntity);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{evaluationId}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long evaluationId) {
        evaluationService.deleteEvaluation(evaluationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<List<Evaluation>> getEvaluationsForStudent(@PathVariable Long studentId) {
        List<Evaluation> evaluations = evaluationService.getEvaluationsForStudent(studentId);
        return ResponseEntity.ok(evaluations);
    }

    @PostMapping("/students/{studentId}/evaluations/{evaluationId}")
    public ResponseEntity<Student> addEvaluationToStudent(@PathVariable Long studentId,
            @PathVariable Long evaluationId) {
        Student updatedStudent = evaluationService.addEvaluationToStudent(studentId, evaluationId);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/students/{studentId}/evaluations/{evaluationId}")
    public ResponseEntity<Void> removeEvaluationFromStudent(@PathVariable Long studentId,
            @PathVariable Long evaluationId) {
        evaluationService.removeEvaluationFromStudent(studentId, evaluationId);
        return ResponseEntity.noContent().build();
    }
}