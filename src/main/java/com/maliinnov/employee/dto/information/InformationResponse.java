package com.maliinnov.employee.dto.information;

import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.models.Employee;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class InformationResponse {

    private Long id;
    private String title;
    private String content;
    private String imagePath;
    private State state;
    private LocalDateTime date;
    private Employee employee;
}