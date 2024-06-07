package cegep.management.system.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cegep.management.system.api.dto.ScheduleDTO;
import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Course;
import cegep.management.system.api.model.Schedule;
import cegep.management.system.api.model.Student;
import cegep.management.system.api.repo.CourseRepository;
import cegep.management.system.api.repo.ScheduleRepository;
import cegep.management.system.api.repo.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, CourseRepository courseRepository,
            StudentRepository studentRepository) {
        this.scheduleRepository = scheduleRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Optional<Schedule> getScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId);
    }

    public List<Schedule> getSchedulesByStudentId(Long studentId) {
        return scheduleRepository.findByStudentId(studentId);
    }

    public Schedule createSchedule(ScheduleDTO scheduleDTO) {
        Student student = studentRepository.findById(scheduleDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Can't find a student with id: " + scheduleDTO.getStudentId()));
        Course course = courseRepository.findById(scheduleDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Can't find a course with id: " + scheduleDTO.getCourseId()));
        return scheduleRepository
                .save(new Schedule(student, course, scheduleDTO.getHourStart(), scheduleDTO.getHourEnd()));
    }

    @Transactional
    public Schedule updateSchedule(Long scheduleId, Schedule updatedSchedule) {
        return scheduleRepository.findById(scheduleId).map(schedule -> {
            schedule.setStudent(updatedSchedule.getStudent());
            schedule.setCourse(updatedSchedule.getCourse());
            schedule.setHourStart(updatedSchedule.getHourStart());
            schedule.setHourEnd(updatedSchedule.getHourEnd());
            return scheduleRepository.save(schedule);
        }).orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
    }

    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }
}
