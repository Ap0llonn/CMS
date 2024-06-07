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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", unique = true) // Ensuring unique constraint
    private Person person;

    private String seniority;

    @ManyToOne
    private Type type;

    public Employee(Person person, String seniority, Type type) {
        this.person = person;
        this.seniority = seniority;
        this.type = type;
    }
}
