package cegep.management.system.api.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.AcademicYear;
import cegep.management.system.api.repo.AcademicYearRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AcademicYearService {
    private final AcademicYearRepository academicYearRepository;

    public AcademicYearService(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }

    public List<AcademicYear> getAllAcademicYears() {
        return this.academicYearRepository.findAll();
    }

    public Optional<AcademicYear> getAcademicYearById(Long id) {
        return this.academicYearRepository.findById(id);
    }

    public Optional<AcademicYear> getAcademicYearByStartDateAndEndDate(Date startDate, Date endDate) {
        return this.academicYearRepository.findByStartDateAndEndDate(startDate, endDate);
    }

    public AcademicYear getCurrentAcademicYear() {
        LocalDate currentDate = LocalDate.now();
        Optional<AcademicYear> academicYearOpt = academicYearRepository.findAcademicYearByDate(currentDate,
                currentDate);
        return academicYearOpt.orElseThrow(() -> new EntityNotFoundException("Current academic year not found"));
    }

    public AcademicYear createAcademicYear(AcademicYear academicYear) {
        return this.academicYearRepository.save(academicYear);
    }

    @Transactional
    public AcademicYear updateAcademicYear(Long id, AcademicYear academicYearDetails) {
        return this.academicYearRepository.findById(id)
                .map(academicYear -> {
                    academicYear.setStartDate(academicYearDetails.getStartDate());
                    academicYear.setEndDate(academicYearDetails.getEndDate());
                    return this.academicYearRepository.save(academicYear);
                })
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear not found with id " + id));
    }

    public void deleteAcademicYear(Long id) {
        if (this.academicYearRepository.existsById(id)) {
            this.academicYearRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("AcademicYear not found with id " + id);
        }
    }

    public AcademicYear getOrCreateNextAcademicYear(LocalDate currentDate) {
        LocalDate academicYearStartDate = LocalDate.of(currentDate.getYear(), 8, 20);
        LocalDate academicYearEndDate = LocalDate.of(currentDate.getYear() + 1, 5, 26);

        Optional<AcademicYear> existingAcademicYear = academicYearRepository
                .findAcademicYearByDate(academicYearStartDate, academicYearEndDate);

        if (existingAcademicYear.isPresent()) {
            return existingAcademicYear.get();
        } else {
            AcademicYear newAcademicYear = new AcademicYear(academicYearStartDate, academicYearEndDate);
            return academicYearRepository.save(newAcademicYear);
        }
    }
}
