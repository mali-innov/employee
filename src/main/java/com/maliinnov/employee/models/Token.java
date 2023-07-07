package com.maliinnov.employee.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true,length = 2048,columnDefinition = "Text")
    private String token;
    private boolean revoked;
    private boolean expired;
    private LocalDateTime createdAt;
    private LocalDateTime logoutAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;
}