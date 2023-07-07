package com.maliinnov.employee.services.token;


import com.maliinnov.employee.models.Employee;

public interface TokenService {

    void save(Employee employee, String token);
    void revokeAllEmployeeTokens(Employee employee);

}