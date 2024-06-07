package cegep.management.system.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cegep.management.system.api.dto.LoginRequest;
import cegep.management.system.api.dto.LoginResponse;
import cegep.management.system.api.interfaces.UserDetails;
import cegep.management.system.api.model.Person;
import cegep.management.system.api.repo.EmployeeRepository;
import cegep.management.system.api.repo.PersonRepository;
import cegep.management.system.api.repo.StudentRepository;
import cegep.management.system.api.repo.TeacherRepository;

@Service
public class AuthService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest) {
        Person person = personRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), person.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        UserDetails userDetail = determineUserDetail(person);

        return new LoginResponse(userDetail);
    }

    private UserDetails determineUserDetail(Person person) {
        if (studentRepository.findByPerson(person).isPresent()) {
            return studentRepository.findByPerson(person).get();
        } else if (teacherRepository.findByEmployee_Person(person).isPresent()) {
            return teacherRepository.findByEmployee_Person(person).get();
        } else if (employeeRepository.findByPerson(person).isPresent()) {
            return employeeRepository.findByPerson(person).get();
        } else {
            throw new RuntimeException("No role associated with this user");
        }
    }
}
