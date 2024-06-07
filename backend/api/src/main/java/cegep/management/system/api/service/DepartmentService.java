package cegep.management.system.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Department;
import cegep.management.system.api.repo.DepartmentRepository;
import jakarta.transaction.Transactional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartment() {
        return this.departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return this.departmentRepository.findById(id);
    }

    public Optional<Department> getDepartmentByName(String name) {
        return this.departmentRepository.findByName(name);
    }

    public Department createDepartment(Department department) {
        return this.departmentRepository.save(department);
    }

    @Transactional
    public Department updateDepartment(Long id, Department departmentDetails) {
        return departmentRepository.findById(id)
                .map(department -> {
                    department.setName(departmentDetails.getName());
                    return departmentRepository.save(department);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
    }

    public void deleteDepartment(Long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Department not found with id " + id);
        }
    }
}
