package com.maliinnov.employee.dto.auth;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AuthenticationRequest {
    private String email;
    private String password;
}
