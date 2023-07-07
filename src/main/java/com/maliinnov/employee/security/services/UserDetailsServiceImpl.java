package com.maliinnov.employee.security.services;

import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.exception.NotFoundException;
import com.maliinnov.employee.models.Employee;
import com.maliinnov.employee.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = repository.findByEmailAndState(email, State.Activate);
        if (employee == null){
            throw new NotFoundException("Email ou mot de passe incorrect");
        }
        return UserDetailsImpl.build(employee);
    }
}
