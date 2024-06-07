package cegep.management.system.api.repo;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cegep.management.system.api.model.AcademicYear;
import cegep.management.system.api.model.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("SELECT s FROM Session s WHERE s.startDate <= :date AND s.endDate >= :date AND s.academicYear = :academicYear")
    Optional<Session> findSessionByDateAndAcademicYear(@Param("date") LocalDate date,
            @Param("academicYear") AcademicYear academicYear);

    @Query("SELECT s FROM Session s WHERE s.academicYear = :academicYear AND s.name = :name AND s.startDate = :startDate AND s.endDate = :endDate")
    Optional<Session> findByAcademicYearAndNameAndStartDateAndEndDate(
            @Param("academicYear") AcademicYear academicYear,
            @Param("name") String name,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
