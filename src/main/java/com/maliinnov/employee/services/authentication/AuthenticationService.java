package com.maliinnov.employee.services.authentication;

import com.maliinnov.employee.dto.auth.AuthenticationRequest;
import com.maliinnov.employee.dto.auth.AuthenticationResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authRequest);
}
