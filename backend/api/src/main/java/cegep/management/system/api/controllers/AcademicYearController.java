package cegep.management.system.api.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cegep.management.system.api.model.AcademicYear;
import cegep.management.system.api.service.AcademicYearService;

@RestController
@RequestMapping("/academicYears")
public class AcademicYearController {
    @Autowired
    private AcademicYearService academicYearService;

    @GetMapping
    public ResponseEntity<List<AcademicYear>> getAllAcademicYears() {
        List<AcademicYear> academicYears = academicYearService.getAllAcademicYears();
        return ResponseEntity.ok(academicYears);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicYear> getAcademicYearById(@PathVariable Long id) {
        AcademicYear academicYear = academicYearService.getAcademicYearById(id)
                .orElseThrow(() -> new RuntimeException("AcadamicYear not found with id: " + id));
        return ResponseEntity.ok(academicYear);
    }

    @PostMapping
    public ResponseEntity<AcademicYear> createAcademicYear(@RequestBody AcademicYear academicYear) {
        AcademicYear newAcademicYear = academicYearService.createAcademicYear(academicYear);
        return ResponseEntity.status(201).body(newAcademicYear);
    }

    @PostMapping("/default")
    public ResponseEntity<AcademicYear> createOrDefault(@RequestBody LocalDate currentDate) {
        AcademicYear academicYear = academicYearService.getOrCreateNextAcademicYear(currentDate);
        return ResponseEntity.status(201).body(academicYear);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicYear> updateAcademicYear(@PathVariable Long id,
            @RequestBody AcademicYear academicYear) {
        AcademicYear updatedAcademicYear = academicYearService.updateAcademicYear(id, academicYear);
        return ResponseEntity.ok(updatedAcademicYear);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcademicYear(@PathVariable Long id) {
        academicYearService.deleteAcademicYear(id);
        return ResponseEntity.noContent().build();
    }
}
