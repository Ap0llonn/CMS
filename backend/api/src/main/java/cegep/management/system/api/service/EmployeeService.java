package cegep.management.system.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cegep.management.system.api.dto.EmployeeDTO;
import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Employee;
import cegep.management.system.api.model.Person;
import cegep.management.system.api.model.Type;
import cegep.management.system.api.repo.EmployeeRepository;
import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PersonService personService;
    private final TypeService typeService;

    public EmployeeService(EmployeeRepository employeeRepository, PersonService personService,
            TypeService typeService) {
        this.employeeRepository = employeeRepository;
        this.personService = personService;
        this.typeService = typeService;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public Employee createEmployee(EmployeeDTO employeeDTO) {
        Type type = typeService.getTypeById(employeeDTO.getTypeId())
                .orElseThrow(() -> new RuntimeException("Can't find type with id: " + employeeDTO.getTypeId()));
        Person person = personService.getUserById(employeeDTO.getPersonId()).orElse(personService.createUser(new Person(
                employeeDTO.getFirstName(), employeeDTO.getLastName(), employeeDTO.getEmail(), employeeDTO.getPhone(),
                employeeDTO.getPassword(), employeeDTO.getDateOfBirth())));

        return employeeRepository.save(new Employee(person, employeeDTO.getSeniority(), type));
    }

    @Transactional
    public Employee updateEmployee(Long employeeId, Employee updatedEmployee) {
        return employeeRepository.findById(employeeId).map(employee -> {
            employee.setPerson(updatedEmployee.getPerson());
            employee.setSeniority(updatedEmployee.getSeniority());
            employee.setType(updatedEmployee.getType());
            return employeeRepository.save(employee);
        }).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
