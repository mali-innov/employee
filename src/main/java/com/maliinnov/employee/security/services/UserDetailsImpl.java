package com.maliinnov.employee.security.services;

import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.models.Employee;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private State state;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String firstName, String lastName, String email, String phoneNumber,
                           String password, State state,
                           Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.state = state;
        this.authorities = roles;
    }

    public static UserDetailsImpl build(Employee user){
        List<GrantedAuthority> roles = user.getRoles().stream()
                .map(appRole -> new SimpleGrantedAuthority(appRole.getName()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.getState(),
                roles
        );
    }

    public static UserDetailsImpl build(Long id, String firstName, String lastName, String email,
                                        String phoneNumber, String password, State state,
                                        Collection<? extends GrantedAuthority> roles) {
        return new UserDetailsImpl(id, firstName, lastName, email, phoneNumber,  password, state, roles);
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}