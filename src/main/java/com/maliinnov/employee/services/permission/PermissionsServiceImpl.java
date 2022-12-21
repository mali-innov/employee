package com.maliinnov.employee.services.permission;

import com.maliinnov.employee.dto.PermissionRequest;
import com.maliinnov.employee.exception.NotFoundException;
import com.maliinnov.employee.models.Employee;
import com.maliinnov.employee.models.Permissions;
import com.maliinnov.employee.repositories.EmployeeRepository;
import com.maliinnov.employee.repositories.PermissionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PermissionsServiceImpl implements PermissionsService {

    private final PermissionsRepository repository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Permissions add(Permissions permissions) {
        return repository.save(permissions);
    }

    @Override
    public void addPermissionToEmployee(PermissionRequest request) {
        Employee employee = employeeRepository.findByEmail(request.getEmail());
        if (employee == null){
            throw new NotFoundException("Aucun employée trouvé avec cette adresse email");
        }
        Permissions permission = repository.findByName(request.getPermissions());
        if (permission == null){
            throw new NotFoundException("Nom de la permission incorrecte");
        }
        employee.getPermissions().add(permission);
    }

    @Override
    public List<Permissions> all() {
        return repository.findAll();
    }
}
