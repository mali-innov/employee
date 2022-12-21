package com.maliinnov.employee.security.services;

import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.models.Employee;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
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
    private String gender;
    private LocalDate dateOfBirth;
    private State state;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String firstName, String lastName, String email, String phoneNumber, String password, String gender, LocalDate dateOfBirth, State state, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.state = state;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Employee employee){
        List<GrantedAuthority> authorities = employee.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getPassword(),
                employee.getGender(),
                employee.getDateOfBirth(),
                employee.getState(),
                authorities
        );
    }

    public static UserDetailsImpl build(Long id, String firstName, String lastName, String email,
                                        String phoneNumber, String password, String gender, LocalDate dateOfBirth,
                                        State state, List<GrantedAuthority> authorities) {
        return new UserDetailsImpl(id, firstName, lastName, email, phoneNumber, password, gender, dateOfBirth,
                state, authorities);
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