package com.maliinnov.employee.utils;

import com.maliinnov.employee.models.Employee;
import com.maliinnov.employee.models.Role;
import com.maliinnov.employee.repositories.EmployeeRepository;
import com.maliinnov.employee.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
@Component
@Transactional
public class SetupLoaderData implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }
        final Role superAdminRole = createAppRoleIfNotFound("SUPER_ADMIN");
        final Role adminRole = createAppRoleIfNotFound("ADMIN");
        final Role employeeRole = createAppRoleIfNotFound("EMPLOYEE");
        final List<Role> adminRoles = new ArrayList<>(Arrays.asList(adminRole, employeeRole, superAdminRole));
        createEmployeeIfNotFound(
                adminRoles);
        alreadySetup = true;
    }

    void createEmployeeIfNotFound(List<Role> roles) {
        Employee employee = employeeRepository.findByEmail("johndoe@gmail.com");
        if (employee == null) {
            employee = new Employee();
            employee.setFirstName("John");
            employee.setLastName("Doe");
            employee.setEmail("johndoe@gmail.com");
            employee.setPassword(passwordEncoder.encode("qwerty"));
            employee.setPhoneNumber("75900085");
            employee.setDateOfBirth(LocalDate.of(2000, 1, 1));
        }
        employee.setRoles(roles);
        employeeRepository.save(employee);
    }

    Role createAppRoleIfNotFound(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role = roleRepository.save(role);
        }
        return role;
    }

}
