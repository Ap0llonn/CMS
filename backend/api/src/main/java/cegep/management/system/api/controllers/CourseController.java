package cegep.management.system.api.controllers;

import cegep.management.system.api.dto.CourseDTO;
import cegep.management.system.api.model.Course;
import cegep.management.system.api.model.Student;
import cegep.management.system.api.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody CourseDTO courseDTO) {
        Course createdCourse = courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course courseDetails) {
        Course updatedCourse = courseService.updateCourse(id, courseDetails);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<Student> addCourseToStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        Student student = courseService.addCourseToStudent(studentId, courseId);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<Void> removeCourseFromStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        courseService.removeCourseFromStudent(studentId, courseId);
        return ResponseEntity.noContent().build();
    }
}
