package com.maliinnov.employee.dto.role;

import com.maliinnov.employee.enums.Roles;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RoleResponse {

    private Long id;
    private Roles name;
}