package com.maliinnov.employee.controllers;


import com.maliinnov.employee.dto.auth.AuthenticationRequest;
import com.maliinnov.employee.dto.auth.AuthenticationResponse;
import com.maliinnov.employee.services.authentication.AuthenticationService;
import com.maliinnov.employee.services.logout.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService service;
    private final LogoutService logoutService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        return service.authenticate(request);
    }

    @GetMapping("/logout")
    @ResponseStatus(value = HttpStatus.OK)
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutService.logout(request, response, authentication);
    }
}