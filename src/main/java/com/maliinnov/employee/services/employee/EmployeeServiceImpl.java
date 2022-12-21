package com.maliinnov.employee.services.employee;

import com.maliinnov.employee.dto.auth.AuthenticationRequest;
import com.maliinnov.employee.dto.auth.AuthenticationResponse;
import com.maliinnov.employee.dto.employee.EmployeeRequest;
import com.maliinnov.employee.dto.employee.EmployeeResponse;
import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.exception.BadRequestException;
import com.maliinnov.employee.exception.NotFoundException;
import com.maliinnov.employee.models.Employee;
import com.maliinnov.employee.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    @Override
    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest) {
        Employee email = repository.findByEmail(employeeRequest.getEmail());
        if (email != null){
            throw new BadRequestException(employeeRequest.getEmail()+" existe déjà");
        }
        Employee phoneNumber = repository.findByPhoneNumber(employeeRequest.getPhoneNumber());
        if (phoneNumber != null){
            throw new BadRequestException(employeeRequest.getPhoneNumber()+" existe déjà");
        }
        Employee employee = this.mapAndSaveEmployee(employeeRequest);
        return this.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeResponse> findAll() {
        List<Employee> employees = repository.findAll();
        List<EmployeeResponse> employeesDto = new ArrayList<>();
        employees.forEach(employee -> {
            employeesDto.add(this.mapToEmployeeDto(employee));
        });
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
    public EmployeeResponse updateEmployee(EmployeeRequest employeeRequest) {
        Employee employee = repository.findByIdAndState(employeeRequest.getId(), State.Activate);
        if (employee == null){
            throw new NotFoundException("Aucun(e) employé(e) trouvé(e)");
        }
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setEmail(employeeRequest.getEmail());
        employee.setPhoneNumber(employeeRequest.getPhoneNumber());
        employee.setGender(employeeRequest.getGender());
        employee.setDateOfBirth(employeeRequest.getDateOfBirth());
        if (employeeRequest.getPassword() != null && !Objects.equals(employeeRequest.getPassword(), "")) {
            employee.setPassword(employeeRequest.getPassword());
        }
        Employee savedEmployee = repository.save(employee);
        return this.mapToEmployeeDto(savedEmployee);
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
    public Employee loadEmployeeByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        Employee employee = repository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (employee != null){
            return this.mapToAuthenticationResponse(employee);
        }
        else {
            throw new NotFoundException("Email ou mot de passe incorrect");
        }
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
                .gender(employee.getGender())
                .permissions(employee.getPermissions())
                .state(employee.getState())
                .build();
    }

    public AuthenticationResponse mapToAuthenticationResponse(Employee employee){
        return AuthenticationResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .gender(employee.getGender())
                .dateOfBirth(employee.getDateOfBirth())
                .state(employee.getState())
                .build();
    }

    public Employee mapAndSaveEmployee(EmployeeRequest employeeRequest){
        Employee employee =  new Employee();
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setPhoneNumber(employeeRequest.getPhoneNumber());
        employee.setEmail(employeeRequest.getEmail());
        employee.setDateOfBirth(employeeRequest.getDateOfBirth());
        employee.setGender(employeeRequest.getGender());
        return repository.save(employee);
    }
}
