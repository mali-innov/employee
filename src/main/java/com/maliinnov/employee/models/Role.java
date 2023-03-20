package com.maliinnov.employee.models;


import com.maliinnov.employee.enums.Roles;
import com.maliinnov.employee.enums.State;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Roles name;
    @Enumerated(EnumType.STRING)
    private State state = State.Activate;

    public Role(Roles name) {
        this.name = name;
    }
}
