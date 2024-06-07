package cegep.management.system.api.model;

import cegep.management.system.api.interfaces.UserDetails;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", unique = true) // Ensuring unique constraint
    private Employee employee;

    @ManyToOne
    private Department department;

    public Teacher(Employee employee, Department department) {
        this.employee = employee;
        this.department = department;
    }
}
