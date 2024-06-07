package cegep.management.system.api.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private LocalDate dateOfBirth;
    private String seniority;
    private Long typeId;
    private Long personId;
    private Long departmentId;
}
