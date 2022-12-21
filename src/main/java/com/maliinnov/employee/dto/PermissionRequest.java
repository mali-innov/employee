package com.maliinnov.employee.dto;

import com.maliinnov.employee.enums.EPermissions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionRequest {

    private String email;
    private EPermissions permissions;
}