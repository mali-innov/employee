package com.maliinnov.employee.services.employee;

import com.maliinnov.employee.dto.auth.AuthenticationRequest;
import com.maliinnov.employee.dto.auth.AuthenticationResponse;
import com.maliinnov.employee.dto.employee.EmployeeRequest;
import com.maliinnov.employee.dto.employee.EmployeeResponse;
import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.models.Employee;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse addEmployee(EmployeeRequest employeeRequest);
    List<EmployeeResponse> findAll();
    EmployeeResponse findById(Long id);
    EmployeeResponse findByIdAndState(Long id, State state);
    EmployeeResponse updateEmployee(EmployeeRequest employeeRequest);
    EmployeeResponse deleteEmployee(Employee employee);
    EmployeeResponse restoreEmployee(Employee employee);
    Employee loadEmployeeByEmail(String email);
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);

    List<EmployeeResponse> findByState(State state);


    EmployeeResponse mapToEmployeeDto(Employee employee);
}
