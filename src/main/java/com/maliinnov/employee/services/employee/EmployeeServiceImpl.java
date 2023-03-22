package com.maliinnov.employee.services.employee;

import com.maliinnov.employee.dto.employee.EmployeeResponse;
import com.maliinnov.employee.dto.role.RoleRequest;
import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.exception.BadRequestException;
import com.maliinnov.employee.exception.NotFoundException;
import com.maliinnov.employee.models.Employee;
import com.maliinnov.employee.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public EmployeeResponse addEmployee(Employee employee) {
        Employee email = repository.findByEmail(employee.getEmail());
        if (email != null){
            throw new BadRequestException(employee.getEmail()+" existe déjà");
        }
        Employee phoneNumber = repository.findByPhoneNumber(employee.getPhoneNumber());
        if (phoneNumber != null){
            throw new BadRequestException(employee.getPhoneNumber()+" existe déjà");
        }
        if (employee.getPassword() != null && !Objects.equals(employee.getPassword(), "")) {
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        }
        return this.mapToEmployeeDto(repository.save(employee));
    }

    @Override
    public List<EmployeeResponse> findAll() {
        List<Employee> employees = repository.findAll();
        List<EmployeeResponse> employeesDto = new ArrayList<>();
        employees.forEach(employee -> employeesDto.add(this.mapToEmployeeDto(employee)));
        return employeesDto;
    }

    @Override
    public EmployeeResponse findById(Long id) {
        Employee employee = repository.getEmployeeById(id);
        if (employee == null){
            throw new NotFoundException("Aucun employée trouvé(e) avec l'id : " + id);
        }
        return this.mapToEmployeeDto(employee);
    }

    @Override
    public EmployeeResponse findByIdAndState(Long id, State state) {
        Employee employee = repository.findByIdAndState(id, State.Activate);
        if (employee == null){
            throw new NotFoundException("Aucun employée trouvé(e) avec l'id : " + id);
        }
        return this.mapToEmployeeDto(employee);
    }

    @Override
    public EmployeeResponse updateEmployee(Employee employees) {
        Employee employee = repository.findByIdAndState(employees.getId(), State.Activate);
        if (employee == null){
            throw new NotFoundException("Aucun(e) employé(e) trouvé(e)");
        }
        employee.setFirstName(employees.getFirstName());
        employee.setLastName(employees.getLastName());
        employee.setEmail(employees.getEmail());
        employee.setPhoneNumber(employees.getPhoneNumber());
        employee.setDateOfBirth(employees.getDateOfBirth());
        if (employees.getPassword() != null && !Objects.equals(employees.getPassword(), "")) {
            employee.setPassword(employees.getPassword());
        }
        return this.mapToEmployeeDto(employee);
    }

    @Override
    public EmployeeResponse addRoleToUser(Long userId, RoleRequest request) {
        Employee employee = repository.findByIdAndState(userId, State.Activate);

        if (employee == null) {
            throw new NotFoundException("Aucun employée trouvé(e)");
        }
        employee.setRoles(request.getRoles());
        return this.mapToEmployeeDto(employee);
    }

    @Override
    public EmployeeResponse deleteEmployee(Employee employee) {
        Employee employeeToDelete = repository.getEmployeeById(employee.getId());
        employeeToDelete.setState(State.Deactivate);
        return this.mapToEmployeeDto(employeeToDelete);
    }

    @Override
    public EmployeeResponse restoreEmployee(Employee employee) {
        Employee employeeToDelete = repository.getEmployeeById(employee.getId());
        employeeToDelete.setState(State.Activate);
        return this.mapToEmployeeDto(employeeToDelete);
    }



    @Override
    public List<EmployeeResponse> findByState(State state) {
        List<Employee> employees = repository.findByState(state);
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        for (Employee employee: employees){
            employeeResponses.add(this.mapToEmployeeDto(employee));
        }
        return employeeResponses;
    }



    @Override
    public EmployeeResponse mapToEmployeeDto(Employee employee){
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .dateOfBirth(employee.getDateOfBirth())
                .phoneNumber(employee.getPhoneNumber())
                .roles(employee.getRoles())
                .state(employee.getState())
                .build();
    }
}
