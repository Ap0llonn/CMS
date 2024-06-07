package cegep.management.system.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cegep.management.system.api.dto.ProgramDTO;
import cegep.management.system.api.model.Program;
import cegep.management.system.api.service.ProgramService;

import java.util.List;

@RestController
@RequestMapping("/programs")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping
    public ResponseEntity<List<Program>> getAllPrograms() {
        List<Program> programs = programService.getAllPrograms();
        return ResponseEntity.ok(programs);
    }

    @GetMapping("/{programId}")
    public ResponseEntity<Program> getProgramById(@PathVariable Long programId) {
        return programService.getProgramById(programId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Program> createProgram(@RequestBody ProgramDTO programDTO) {
        Program createdProgram = programService.createProgram(programDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProgram);
    }

    @PutMapping("/{programId}")
    public ResponseEntity<Program> updateProgram(@PathVariable Long programId, @RequestBody Program updatedProgram) {
        return programService.getProgramById(programId)
                .map(program -> {
                    Program updatedProgramEntity = programService.updateProgram(programId, updatedProgram);
                    return ResponseEntity.ok(updatedProgramEntity);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long programId) {
        programService.deleteProgram(programId);
        return ResponseEntity.noContent().build();
    }
}