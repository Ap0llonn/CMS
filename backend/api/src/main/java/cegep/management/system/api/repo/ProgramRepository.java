package cegep.management.system.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cegep.management.system.api.model.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

}