package com.maliinnov.employee.repositories;

import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.models.Information;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InformationRepository extends JpaRepository<Information, Long> {

    List<Information> findByStateOrderByIdDesc(State state);
    Information findByIdAndState(Long id, State state);
}
