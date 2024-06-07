package cegep.management.system.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import cegep.management.system.api.model.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

}
