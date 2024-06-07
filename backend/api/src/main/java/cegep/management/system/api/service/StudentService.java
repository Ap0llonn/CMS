package cegep.management.system.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cegep.management.system.api.dto.StudentDTO;
import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Person;
import cegep.management.system.api.model.Program;
import cegep.management.system.api.model.Session;
import cegep.management.system.api.model.Student;
import cegep.management.system.api.repo.ProgramRepository;
import cegep.management.system.api.repo.SessionRepository;
import cegep.management.system.api.repo.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final PersonService personService;
    private final ProgramRepository programRepository;
    private final SessionRepository sessionRepository;
    private final SessionService sessionService;

    public StudentService(StudentRepository studentRepository,
            PersonService personService, ProgramRepository programRepository,
            SessionRepository sessionRepository, SessionService sessionService) {
        this.studentRepository = studentRepository;
        this.personService = personService;
        this.programRepository = programRepository;
        this.sessionRepository = sessionRepository;
        this.sessionService = sessionService;
    }

    // Basic CRUD op for the studentList
    public List<Student> findAllStudent() {
        return this.studentRepository.findAll();
    }

    public Optional<Student> findStudentById(Long id) {
        return this.studentRepository.findById(id);
    }

    public Student createStudent(StudentDTO studentDTO) {
        Person person = new Person(studentDTO.getFirstName(), studentDTO.getLastName(), studentDTO.getEmail(),
                studentDTO.getPhone(), studentDTO.getPassword(), studentDTO.getDateOfBirth());

        if (this.personService.createUser(person) != null) {
            Program program = this.programRepository.findById(studentDTO.getProgramId())
                    .orElseThrow(
                            () -> new EntityNotFoundException(
                                    "Program not found with ID: " + studentDTO.getProgramId()));

            Session session;
            if (studentDTO.getSessionId() == null) {
                session = this.sessionService.getOrCreateSessionForCurrentDate();
            } else {
                session = this.sessionRepository.findById(studentDTO.getSessionId())
                        .orElseThrow(
                                () -> new EntityNotFoundException(
                                        "Session not found with ID: " + studentDTO.getSessionId()));
            }

            Student student = new Student();
            student.setPerson(person);
            student.setProgram(program);
            student.setSession(session);
            student.setField(studentDTO.getField());

            return this.studentRepository.save(student);
        }
        throw new RuntimeException("Failed to create person");
    }

    @Transactional
    public Student updateStudent(Long studentId, Student updatedStudent) {
        return studentRepository.findById(studentId).map(student -> {
            student.setProgram(updatedStudent.getProgram());
            student.setSession(updatedStudent.getSession());
            student.setField(updatedStudent.getField());
            return studentRepository.save(student);
        }).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    @Transactional
    public Student updateStudentProgram(Long studentId, Long programId) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    Program program = programRepository.findById(programId)
                            .orElseThrow(() -> new ResourceNotFoundException("Program not found"));
                    student.setProgram(program);
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    public void deleteStudent(Long studentId) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
        } else {
            throw new ResourceNotFoundException("Student not found");
        }
    }
}
