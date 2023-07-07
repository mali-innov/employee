package com.maliinnov.employee.dto.auth;

import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.models.Role;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AuthenticationResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<Role> roles;
    private State state;
    private String accessToken;
}
