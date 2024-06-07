package cegep.management.system.api.dto;

import cegep.management.system.api.interfaces.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private UserDetails userDetail;
}