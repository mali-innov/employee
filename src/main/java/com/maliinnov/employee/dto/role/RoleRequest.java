package com.maliinnov.employee.dto.role;

import com.maliinnov.employee.models.Role;
import lombok.*;

import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RoleRequest {
    private List<Role> roles;
}