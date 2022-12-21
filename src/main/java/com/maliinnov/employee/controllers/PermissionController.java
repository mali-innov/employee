package com.maliinnov.employee.controllers;

import com.maliinnov.employee.dto.PermissionRequest;
import com.maliinnov.employee.models.Permissions;
import com.maliinnov.employee.services.permission.PermissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final PermissionsService service;

    @PostMapping("")
    ResponseEntity<Permissions> add(@RequestBody Permissions permissions){
        return new ResponseEntity<>(service.add(permissions), HttpStatus.CREATED);
    }

    @PostMapping("/addPermissionToEmployee")
    void addPermissionToEmployee(@RequestBody PermissionRequest request){
        service.addPermissionToEmployee(request);
    }

    @GetMapping("")
    ResponseEntity<List<Permissions>> all(){
        return new ResponseEntity<>(service.all(), HttpStatus.OK);
    }
}
