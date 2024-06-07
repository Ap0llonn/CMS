package cegep.management.system.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cegep.management.system.api.dto.ProgramDTO;
import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Department;
import cegep.management.system.api.model.Program;
import cegep.management.system.api.repo.DepartmentRepository;
import cegep.management.system.api.repo.ProgramRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;
    private final DepartmentRepository departmentRepository;

    public ProgramService(ProgramRepository programRepository, DepartmentRepository departmentRepository) {
        this.programRepository = programRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Program> getAllPrograms() {
        return programRepository.findAll();
    }

    public Optional<Program> getProgramById(Long programId) {
        return programRepository.findById(programId);
    }

    public Program createProgram(ProgramDTO programDTO) {
        Department department = departmentRepository.findById(programDTO.getDepartmentId()).orElseThrow(
                () -> new RuntimeException("Can't find a department with id: " + programDTO.getDepartmentId()));
        return programRepository.save(new Program(programDTO.getName(), department));
    }

    @Transactional
    public Program updateProgram(Long programId, Program updatedProgram) {
        return programRepository.findById(programId).map(program -> {
            program.setName(updatedProgram.getName());
            program.setDepartment(updatedProgram.getDepartment());
            return programRepository.save(program);
        }).orElseThrow(() -> new ResourceNotFoundException("Program not found"));
    }

    public void deleteProgram(Long programId) {
        programRepository.deleteById(programId);
    }
}
