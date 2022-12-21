package com.maliinnov.employee.utils;

import com.maliinnov.employee.enums.EPermissions;
import com.maliinnov.employee.models.Employee;
import com.maliinnov.employee.models.Permissions;
import com.maliinnov.employee.repositories.EmployeeRepository;
import com.maliinnov.employee.repositories.PermissionsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.maliinnov.employee.enums.EPermissions.*;


@RequiredArgsConstructor
@Component
@Transactional
public class SetupLoaderData implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;
    private final EmployeeRepository employeeRepository;
    private final PermissionsRepository permissionsRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }
        final Permissions createPermissions = createPermissionsIfNotFound(CREATE);
        final Permissions readPermissions = createPermissionsIfNotFound(READ);
        final Permissions updatePermissions = createPermissionsIfNotFound(UPDATE);
        final Permissions deletePermissions = createPermissionsIfNotFound(DELETE);
        final Set<Permissions> adminPermissions = new HashSet<>(Arrays.asList(
                createPermissions, readPermissions, updatePermissions, deletePermissions));
        createEmployeeIfNotFound("John", "Doe", "johndoe@gmail.com",
                "75900085", "qwerty", adminPermissions);
        alreadySetup = true;
    }

    void createEmployeeIfNotFound(String firstName, String lastName, String email, String phoneNumber,
                                  String password, Set<Permissions> permissions) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee == null) {
            employee = new Employee();
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setEmail(email);
            employee.setPassword(passwordEncoder.encode(password));
            employee.setPhoneNumber(phoneNumber);
        }
        employee.setPermissions(permissions);
        employee = employeeRepository.save(employee);
    }

    Permissions createPermissionsIfNotFound(final EPermissions name) {
        Permissions permissions = permissionsRepository.findByName(name);
        if (permissions == null) {
            permissions = new Permissions(name);
            permissions = permissionsRepository.save(permissions);
        }
        return permissions;
    }

}
