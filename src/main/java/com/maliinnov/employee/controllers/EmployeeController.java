package com.maliinnov.employee.controllers;

import com.maliinnov.employee.dto.employee.EmployeeRequest;
import com.maliinnov.employee.dto.employee.EmployeeResponse;
import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.models.Employee;
import com.maliinnov.employee.services.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {


    private final EmployeeService service;

    @PostMapping("")
    ResponseEntity<EmployeeResponse> addEmployee(@RequestBody EmployeeRequest employeeRequest){
        return new ResponseEntity<>(service.addEmployee(employeeRequest), HttpStatus.CREATED);
    }

    @GetMapping("")
    ResponseEntity<List<EmployeeResponse>> all(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<EmployeeResponse> findById(@PathVariable Long id){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/state/{state}")
    ResponseEntity<EmployeeResponse> findByIdAndState(@PathVariable Long id, @PathVariable State state){
        return new ResponseEntity<>(service.findByIdAndState(id, state), HttpStatus.OK);
    }

    @PutMapping
    ResponseEntity<EmployeeResponse> update(@RequestBody EmployeeRequest employeeRequest){
        return new ResponseEntity<>(service.updateEmployee(employeeRequest), HttpStatus.OK);
    }

    @GetMapping("/state/{state}")
    ResponseEntity<List<EmployeeResponse>> findByState(@PathVariable State state){
        return new ResponseEntity<>(service.findByState(state), HttpStatus.OK);
    }

    @PutMapping("/delete")
    ResponseEntity<EmployeeResponse> deleteEmployee(@RequestBody Employee employee){
        return new ResponseEntity<>(service.deleteEmployee(employee), HttpStatus.OK);
    }

    @PutMapping("/restore")
    ResponseEntity<EmployeeResponse> restoreEmployee(@RequestBody Employee employee){
        return new ResponseEntity<>(service.restoreEmployee(employee), HttpStatus.OK);
    }


}
