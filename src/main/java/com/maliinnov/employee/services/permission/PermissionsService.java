package com.maliinnov.employee.services.permission;

import com.maliinnov.employee.dto.PermissionRequest;
import com.maliinnov.employee.models.Permissions;

import java.util.List;

public interface PermissionsService {

    Permissions add(Permissions permissions);
    void addPermissionToEmployee(PermissionRequest request);
    List<Permissions> all();
}