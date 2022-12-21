package com.maliinnov.employee.dto.employee;

import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.models.Permissions;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Data
@ToString
public class EmployeeRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String gender;
    private LocalDate dateOfBirth;
    private State state;
    private Set<Permissions> permissions;
}
