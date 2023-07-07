package com.maliinnov.employee.models;

import com.maliinnov.employee.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Information {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition="TEXT", length = 2048)
    private String content;
    private String imagePath;
    @Enumerated(EnumType.STRING)
    private State state = State.Deactivate;
    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    private Employee employee;
}
