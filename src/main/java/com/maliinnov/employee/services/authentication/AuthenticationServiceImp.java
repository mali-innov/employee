package com.maliinnov.employee.services.authentication;

import com.maliinnov.employee.dto.auth.AuthenticationRequest;
import com.maliinnov.employee.dto.auth.AuthenticationResponse;
import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.exception.NotFoundException;
import com.maliinnov.employee.models.Employee;
import com.maliinnov.employee.models.Role;
import com.maliinnov.employee.repositories.EmployeeRepository;
import com.maliinnov.employee.repositories.RoleRepository;
import com.maliinnov.employee.security.jwt.JwtService;
import com.maliinnov.employee.security.services.UserDetailsImpl;
import com.maliinnov.employee.services.token.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;
    private final EmployeeRepository employeeRepository;
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        List<Role> roleList = new ArrayList<>();
        List<String> roleNames = Arrays.asList("SUPER_ADMIN", "ADMIN");

        for (String authority : roles) {
            if (roleNames.contains(authority)) {
                roleList.add(roleName(authority));
            }
        }


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName();
            Employee author = employeeRepository.findByEmailAndState(email, State.Activate);
            tokenService.revokeAllEmployeeTokens(author);
            tokenService.save(author, token);
        }
            return new AuthenticationResponse(
                    userDetails.getId(),
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    userDetails.getEmail(),
                    userDetails.getPhoneNumber(),
                    roleList,
                    userDetails.getState(),
                    token
            );
    }

    Role roleName(String name){
        Role roleName = roleRepository.findByName(name);
        if (roleName == null){
            throw new NotFoundException("Role incorrect");
        }
        return roleName;
    }
}
