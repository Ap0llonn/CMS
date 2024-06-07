package cegep.management.system.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cegep.management.system.api.dto.TeacherDTO;
import cegep.management.system.api.model.Course;
import cegep.management.system.api.model.Student;
import cegep.management.system.api.model.Teacher;
import cegep.management.system.api.service.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long teacherId) {
        return teacherService.getTeacherById(teacherId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{teacherId}/courses")
    public ResponseEntity<List<Course>> getAllCoursesForTeacher(@PathVariable Long teacherId) {
        List<Course> courses = teacherService.getAllCoursesForTeacher(teacherId);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{teacherId}/students")
    public ResponseEntity<List<Student>> getAllStudentsForTeacher(@PathVariable Long teacherId) {
        List<Student> students = teacherService.getAllStudentsForTeacher(teacherId);
        return ResponseEntity.ok(students);
    }

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        Teacher createdTeacher = teacherService.createTeacher(teacherDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacher);
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long teacherId, @RequestBody Teacher updatedTeacher) {
        return teacherService.getTeacherById(teacherId)
                .map(teacher -> {
                    Teacher updatedTeacherEntity = teacherService.updateTeacher(teacherId, updatedTeacher);
                    return ResponseEntity.ok(updatedTeacherEntity);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{teacherId}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long teacherId) {
        teacherService.deleteTeacher(teacherId);
        return ResponseEntity.noContent().build();
    }
}