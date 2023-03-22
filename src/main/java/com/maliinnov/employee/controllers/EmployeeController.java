package com.maliinnov.employee.controllers;

import com.maliinnov.employee.dto.employee.EmployeeResponse;
import com.maliinnov.employee.dto.role.RoleRequest;
import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.models.Employee;
import com.maliinnov.employee.services.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {


    private final EmployeeService service;

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @PostMapping("")
    ResponseEntity<EmployeeResponse> addEmployee(@RequestBody Employee employee){
        return new ResponseEntity<>(service.addEmployee(employee), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    @GetMapping("")
    ResponseEntity<List<EmployeeResponse>> all(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    @GetMapping("/{id}")
    ResponseEntity<EmployeeResponse> findById(@PathVariable Long id){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    @GetMapping("/{id}/state/{state}")
    ResponseEntity<EmployeeResponse> findByIdAndState(@PathVariable Long id, @PathVariable State state){
        return new ResponseEntity<>(service.findByIdAndState(id, state), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PutMapping
    ResponseEntity<EmployeeResponse> update(@RequestBody Employee employee){
        return new ResponseEntity<>(service.updateEmployee(employee), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PutMapping("/role/{employeeId}")
    @ResponseStatus(value = HttpStatus.OK)
    EmployeeResponse addRoleToUser(@PathVariable Long employeeId, @RequestBody RoleRequest roles) {
        return service.addRoleToUser(employeeId, roles);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EMPLOYEE')")
    @GetMapping("/state/{state}")
    ResponseEntity<List<EmployeeResponse>> findByState(@PathVariable State state){
        return new ResponseEntity<>(service.findByState(state), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PutMapping("/delete")
    ResponseEntity<EmployeeResponse> deleteEmployee(@RequestBody Employee employee){
        return new ResponseEntity<>(service.deleteEmployee(employee), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PutMapping("/restore")
    ResponseEntity<EmployeeResponse> restoreEmployee(@RequestBody Employee employee){
        return new ResponseEntity<>(service.restoreEmployee(employee), HttpStatus.OK);
    }
}
