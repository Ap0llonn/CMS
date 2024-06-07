package cegep.management.system.api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cegep.management.system.api.model.StudentCourse;
import cegep.management.system.api.model.StudentCourseId;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseId> {
    List<StudentCourse> findAllByIdStudentId(Long studentId);

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.id.courseId = :courseId")
    List<StudentCourse> findAllByCourseId(@Param("courseId") Long courseId);
}
