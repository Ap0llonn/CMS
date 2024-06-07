package cegep.management.system.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cegep.management.system.api.dto.EvaluationDTO;
import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Course;
import cegep.management.system.api.model.Evaluation;
import cegep.management.system.api.model.Student;
import cegep.management.system.api.model.StudentEvaluation;
import cegep.management.system.api.model.StudentEvaluationId;
import cegep.management.system.api.repo.EvaluationRepository;
import cegep.management.system.api.repo.StudentEvaluationRepository;
import cegep.management.system.api.repo.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final StudentRepository studentRepository;
    private final StudentEvaluationRepository studentEvaluationRepository;
    private final CourseService courseService;

    public EvaluationService(EvaluationRepository evaluationRepository,
            StudentRepository studentRepository,
            StudentEvaluationRepository studentEvaluationRepository, CourseService courseService) {
        this.evaluationRepository = evaluationRepository;
        this.studentRepository = studentRepository;
        this.studentEvaluationRepository = studentEvaluationRepository;
        this.courseService = courseService;
    }

    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    public Optional<Evaluation> getEvaluationById(Long evaluationId) {
        return evaluationRepository.findById(evaluationId);
    }

    public Evaluation createEvaluation(EvaluationDTO evaluationDTO) {
        Course course = courseService.getCourseById(evaluationDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Can't find a course with id: " + evaluationDTO.getCourseId()));
        return evaluationRepository.save(new Evaluation(evaluationDTO.getName(), course, evaluationDTO.getPonderation(),
                evaluationDTO.getDenominator()));
    }

    @Transactional
    public Evaluation updateEvaluation(Long evaluationId, Evaluation updatedEvaluation) {
        return evaluationRepository.findById(evaluationId).map(evaluation -> {
            evaluation.setName(updatedEvaluation.getName());
            evaluation.setCourse(updatedEvaluation.getCourse());
            evaluation.setPonderation(updatedEvaluation.getPonderation());
            evaluation.setDenominator(updatedEvaluation.getDenominator());
            return evaluationRepository.save(evaluation);
        }).orElseThrow(() -> new ResourceNotFoundException("Evaluation not found"));
    }

    public void deleteEvaluation(Long evaluationId) {
        evaluationRepository.deleteById(evaluationId);
    }

    public List<Evaluation> getEvaluationsForStudent(Long studentId) {
        List<StudentEvaluation> studentEvaluations = studentEvaluationRepository.findAllByIdStudentId(studentId);
        return studentEvaluations.stream()
                .map(se -> evaluationRepository.findById(se.getId().getEvaluationId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Transactional
    public Student addEvaluationToStudent(Long studentId, Long evaluationId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Evaluation> evaluationOpt = evaluationRepository.findById(evaluationId);

        if (studentOpt.isPresent() && evaluationOpt.isPresent()) {
            Student student = studentOpt.get();
            Evaluation evaluation = evaluationOpt.get();
            StudentEvaluation studentEvaluation = new StudentEvaluation(studentId, evaluationId);
            studentEvaluation.setStudent(student);
            studentEvaluation.setEvaluation(evaluation);
            studentEvaluationRepository.save(studentEvaluation);
            return student;
        } else {
            throw new ResourceNotFoundException("Student or Evaluation not found");
        }
    }

    @Transactional
    public void removeEvaluationFromStudent(Long studentId, Long evaluationId) {
        StudentEvaluationId id = new StudentEvaluationId(studentId, evaluationId);
        if (studentEvaluationRepository.existsById(id)) {
            studentEvaluationRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("StudentEvaluation not found");
        }
    }
}
