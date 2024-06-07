package cegep.management.system.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cegep.management.system.api.dto.EmployeeDTO;
import cegep.management.system.api.dto.TeacherDTO;
import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Course;
import cegep.management.system.api.model.Department;
import cegep.management.system.api.model.Employee;
import cegep.management.system.api.model.Student;
import cegep.management.system.api.model.StudentCourse;
import cegep.management.system.api.model.Teacher;
import cegep.management.system.api.repo.StudentCourseRepository;
import cegep.management.system.api.repo.TeacherRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseService courseService;
    private final StudentCourseRepository studentCourseRepository;
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public TeacherService(TeacherRepository teacherRepository, CourseService courseService,
            StudentCourseRepository studentCourseRepository, EmployeeService employeeService,
            DepartmentService departmentService) {
        this.teacherRepository = teacherRepository;
        this.courseService = courseService;
        this.studentCourseRepository = studentCourseRepository;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId);
    }

    public List<Course> getAllCoursesForTeacher(Long teacherId) {
        return courseService.getAllCourses()
                .stream()
                .filter(course -> course.getTeacher()
                        .equals(teacherRepository.findById(teacherId).orElseThrow(
                                () -> new RuntimeException("Can't find a teacher with the id: " + teacherId))))
                .collect(Collectors.toList());
    }

    public List<Student> getAllStudentsForTeacher(Long teacherId) {
        return getAllCoursesForTeacher(teacherId).stream()
                .flatMap(course -> studentCourseRepository.findAllByCourseId(course.getId()).stream()
                        .map(StudentCourse::getStudent))
                .distinct()
                .collect(Collectors.toList());
    }

    public Teacher createTeacher(TeacherDTO teacherDTO) {
        EmployeeDTO dto = createEmployeeDTO(teacherDTO);
        Department department = departmentService.getDepartmentById(teacherDTO.getDepartmentId()).orElseThrow(
                () -> new RuntimeException("Can't find a department with id: " + teacherDTO.getDepartmentId()));

        Employee employee = employeeService.createEmployee(dto);
        return teacherRepository.save(new Teacher(employee, department));
    }

    private EmployeeDTO createEmployeeDTO(TeacherDTO teacherDTO) {
        return new EmployeeDTO(teacherDTO.getFirstName(), teacherDTO.getLastName(), teacherDTO.getEmail(),
                teacherDTO.getPhone(), teacherDTO.getPassword(), teacherDTO.getDateOfBirth(), teacherDTO.getSeniority(),
                teacherDTO.getTypeId(), teacherDTO.getPersonId());
    }

    @Transactional
    public Teacher updateTeacher(Long teacherId, Teacher updatedTeacher) {
        return teacherRepository.findById(teacherId).map(teacher -> {
            teacher.setEmployee(updatedTeacher.getEmployee());
            teacher.setDepartment(updatedTeacher.getDepartment());
            return teacherRepository.save(teacher);
        }).orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
    }

    public void deleteTeacher(Long teacherId) {
        teacherRepository.deleteById(teacherId);
    }
}
