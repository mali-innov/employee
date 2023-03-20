package com.maliinnov.employee.services.employee;

import com.maliinnov.employee.dto.employee.EmployeeResponse;
import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.models.Employee;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse addEmployee(Employee employee);
    List<EmployeeResponse> findAll();
    EmployeeResponse findById(Long id);
    EmployeeResponse findByIdAndState(Long id, State state);
    EmployeeResponse updateEmployee(Employee employee);
    EmployeeResponse deleteEmployee(Employee employee);
    EmployeeResponse restoreEmployee(Employee employee);

    List<EmployeeResponse> findByState(State state);

    EmployeeResponse mapToEmployeeDto(Employee employee);
}
