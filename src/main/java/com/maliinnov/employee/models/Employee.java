package com.maliinnov.employee.models;

import com.maliinnov.employee.enums.State;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.*;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private LocalDate dateOfBirth;


    @Enumerated(EnumType.STRING)
    private State state = State.Activate;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

}