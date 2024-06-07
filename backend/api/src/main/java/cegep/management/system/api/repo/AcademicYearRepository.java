package cegep.management.system.api.repo;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cegep.management.system.api.model.AcademicYear;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {
    Optional<AcademicYear> findByStartDateAndEndDate(Date startDate, Date endDate);

    @Query("SELECT a FROM AcademicYear a WHERE :startDate BETWEEN a.startDate AND a.endDate OR :endDate BETWEEN a.startDate AND a.endDate")
    Optional<AcademicYear> findAcademicYearByDate(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}