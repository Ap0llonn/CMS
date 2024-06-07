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
public class StudentDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private LocalDate dateOfBirth;
    private Long programId;
    private Long sessionId;
    private String field;

    @Override
    public String toString() {
        return "StudentDTO [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email +
                ", phone=" + phone + ", programId=" + programId + ", sessionId=" + sessionId +
                ", field=" + field + "]";
    }
}
