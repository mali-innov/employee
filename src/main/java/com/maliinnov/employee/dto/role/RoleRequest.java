package com.maliinnov.employee.dto.role;

import com.maliinnov.employee.models.Role;
import lombok.*;

import java.util.Set;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RoleRequest {
    private Set<Role> roles;
}