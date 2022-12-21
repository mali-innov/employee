package com.maliinnov.employee.controllers;

import com.maliinnov.employee.dto.auth.AuthenticationRequest;
import com.maliinnov.employee.dto.auth.AuthenticationResponse;
import com.maliinnov.employee.models.Employee;
import com.maliinnov.employee.security.TokenProvider;
import com.maliinnov.employee.security.services.UserDetailsImpl;
import com.maliinnov.employee.services.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final EmployeeService employeeService;

    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    AuthenticationResponse auth(@RequestBody AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Employee authEmployee = employeeService.loadEmployeeByEmail(request.getEmail());

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> permissions = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        List<GrantedAuthority> authorities = authEmployee.getPermissions().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        UserDetailsImpl userPrincipal = UserDetailsImpl.build(
                authEmployee.getId(),
                authEmployee.getFirstName(),
                authEmployee.getLastName(),
                authEmployee.getEmail(),
                authEmployee.getPhoneNumber(),
                authEmployee.getPassword(),
                authEmployee.getGender(),
                authEmployee.getDateOfBirth(),
                authEmployee.getState(),
                authorities

        );

        String token = tokenProvider.generateJwtToken(userPrincipal);
        return new AuthenticationResponse(
                authEmployee.getId(),
                authEmployee.getFirstName(),
                authEmployee.getLastName(),
                authEmployee.getEmail(),
                authEmployee.getPhoneNumber(),
                authEmployee.getGender(),
                authEmployee.getDateOfBirth(),
                permissions,
                authEmployee.getState(),
                token
        );
    }

}
