package com.maliinnov.employee.dto.role;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RoleResponse {

    private Long id;
    private String name;
}