package com.maliinnov.employee.repositories;

import com.maliinnov.employee.enums.EPermissions;
import com.maliinnov.employee.models.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionsRepository extends JpaRepository<Permissions, Long> {

    Permissions findByName(EPermissions name);
}
