package com.maliinnov.employee.repositories;

import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByEmail(String email);
    Employee findByPhoneNumber(String phoneNumber);
    Employee findByEmailAndPassword(String email, String password);
    Employee getEmployeeById(Long id);

    Employee findByIdAndState(Long employee, State state);

    List<Employee> findByState(State state);
}
