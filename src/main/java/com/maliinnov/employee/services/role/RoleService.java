package com.maliinnov.employee.services.role;

import com.maliinnov.employee.dto.role.RoleRequest;
import com.maliinnov.employee.dto.role.RoleResponse;
import com.maliinnov.employee.enums.Roles;

import java.util.List;

public interface RoleService {
    RoleResponse findById(Long id);
    List<RoleResponse> findAll();
}